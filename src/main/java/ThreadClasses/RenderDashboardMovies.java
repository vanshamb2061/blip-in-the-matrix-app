package ThreadClasses;


import sample.DashboardController;

public class RenderDashboardMovies implements Runnable{
    DashboardController dashboardController;
    public RenderDashboardMovies(DashboardController dashboardController){
        this.dashboardController = dashboardController;
    }
    @Override
    public void run() {

        dashboardController.updateMoviesOnDashboard();
        dashboardController.updateSideMovieOnDashboard();

    }
}
