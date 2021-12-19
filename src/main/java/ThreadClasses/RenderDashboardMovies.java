package ThreadClasses;

import java.util.ArrayList;

public class RenderDashboardMovies implements Runnable{
    sample.dashboardController dashboardController;
    public RenderDashboardMovies(sample.dashboardController dashboardController){
        this.dashboardController = dashboardController;
    }
    @Override
    public void run() {
        System.out.println("hellow this is a thread");

        /*movies = new ArrayList<>();*/
        dashboardController.updateMoviesOnDashboard();
        dashboardController.updateSideMovieOnDashboard();
    }
}
