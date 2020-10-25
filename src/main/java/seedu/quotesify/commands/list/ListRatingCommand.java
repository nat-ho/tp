package seedu.quotesify.commands.list;

import seedu.quotesify.lists.ListManager;
import seedu.quotesify.rating.Rating;
import seedu.quotesify.rating.RatingList;
import seedu.quotesify.rating.RatingParser;
import seedu.quotesify.store.Storage;
import seedu.quotesify.ui.TextUi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListRatingCommand extends ListCommand {

    public ListRatingCommand(String arguments) {
        super(arguments);
    }

    public void execute(TextUi ui, Storage storage) {
        RatingList ratings = (RatingList) ListManager.getList(ListManager.RATING_LIST);
        listRatings(ratings, ui);
        storage.save();
    }

    private void listRatings(RatingList ratingList, TextUi ui) {
        ArrayList<Rating> ratings = ratingList.getList();
        ratings.sort(Comparator.comparing(Rating::getRating));
        Collections.reverse(ratings);
        if (information.isEmpty()) {
            listAllRatings(ratingList, ui);
        } else {
            listSpecifiedRating(ratingList, ui);
        }
    }

    private void listAllRatings(RatingList ratingList, TextUi ui) {
        ui.printAllRatings(ratingList);
    }

    private void listSpecifiedRating(RatingList ratings, TextUi ui) {
        assert !information.isEmpty() : "Rating details should not be empty";
        int ratingToPrint = RatingParser.checkValidityOfRatingScore(information);

        if (ratingToPrint == 0) {
            return;
        }

        boolean isFound = false;
        for (Rating rating : ratings.getList()) {
            if (rating.getRating() == ratingToPrint) {
                isFound = true;
                break;
            }
        }

        if (isFound) {
            ui.printSpecifiedRating(ratings, ratingToPrint);
        } else {
            System.out.printf((LIST_SPECIFIED_RATING_NOT_FOUND_MESSAGE) + "\n", ratingToPrint);
        }
    }
}