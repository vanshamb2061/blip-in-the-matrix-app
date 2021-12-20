package ThreadClasses;


public class RenderDashboardMovies implements Runnable{
    sample.dashboardController dashboardController;
    public RenderDashboardMovies(sample.dashboardController dashboardController){
        this.dashboardController = dashboardController;
    }
    @Override
    public void run() {

        dashboardController.updateMoviesOnDashboard();
        dashboardController.updateSideMovieOnDashboard();

    }
}
