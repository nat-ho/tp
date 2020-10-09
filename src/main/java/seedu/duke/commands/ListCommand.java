package seedu.duke.commands;

import seedu.duke.category.Category;
import seedu.duke.category.CategoryList;
import seedu.duke.lists.ListManager;
import seedu.duke.rating.Rating;
import seedu.duke.rating.RatingList;
import seedu.duke.ui.TextUi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListCommand extends Command {
    private String type;
    private String information;

    public ListCommand(String arguments) {
        String[] details = arguments.split(" ", 2);

        // if user did not provide arguments, let details[1] be empty string
        if (details.length == 1) {
            details = new String[]{details[0], ""};
        }
        type = details[0];
        information = details[1];

//        System.out.println(type);
//        System.out.println(information);
    }

    @Override
    public void execute(TextUi ui, ListManager listManager) {
        switch (type) {
        case TAG_CATEGORY:
            CategoryList categoryList = (CategoryList) listManager.getList(ListManager.CATEGORY_LIST);
            listCategories(categoryList, ui);
            break;
        case TAG_RATING:
            RatingList ratingList = (RatingList) listManager.getList(ListManager.RATING_LIST);
            listRatings(ratingList, ui);
            break;
        default:
        }
    }

    private void listRatings(RatingList ratingList, TextUi ui) {
        ArrayList<Rating> ratings = ratingList.getList();
        ratings.sort(Comparator.comparing(Rating::getRating));
        Collections.reverse(ratings);
        if (information == "") {
            System.out.println("Planning to recommend some books? Here are your rated books!");
            System.out.println(ratingList.toString());
        } else {
            int ratingToList = Integer.parseInt(information);
            if (!(ratingToList >= RATING_ONE && ratingToList <= RATING_FIVE)) {
                System.out.println("That score is out of our range my friend");
                return;
            }
            System.out.println("Here are the books you rated as " + ratingToList + " star!");
            for (Rating rating : ratings) {
                if (rating.getRating() == ratingToList) {
                    System.out.println(rating.getTitleOfRatedBook());
                }
            }
        }
    }

    private void listCategories(CategoryList categoryList, TextUi ui) {
        if ((information.isEmpty())) {
            listAllCategories(categoryList, ui);
        } else {
            listCategory(categoryList, ui);
        }
    }

    private void listAllCategories(CategoryList categoryList, TextUi ui) {
        ui.printAllCategories(categoryList);
    }

    private void listCategory(CategoryList categoryList, TextUi ui) {
        try {
            ui.printCategory(categoryList.getCategoryByName(information));
        } catch (NullPointerException e) {
            ui.printErrorMessage(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
