package pl.auroramc.commons.bukkit.page.navigation;

import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;

public final class NavigationUtils {

  private NavigationUtils() {}

  public static void navigate(
      final NavigationDirection direction, final Gui subjectGui, final PaginatedPane subject) {
    if (whetherPaneIsNotEmpty(subject)) {
      final int currPage = subject.getPage();
      final int nextPage = direction.navigate(subject.getPages(), subject.getPage());
      if (currPage == nextPage) {
        return;
      }

      subject.setPage(nextPage);
      subjectGui.update();
    }
  }

  private static boolean whetherPaneIsNotEmpty(final PaginatedPane pane) {
    return pane.getPages() > 0;
  }
}
