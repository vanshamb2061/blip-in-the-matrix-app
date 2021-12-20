package ThreadClasses;


public class RenderDashboardMovies implements Runnable{
    sample.dashboardController dashboardController;
    public RenderDashboardMovies(sample.dashboardController dashboardController){
        this.dashboardController = dashboardController;
    }
    @Override
    public void run() {
        System.out.println("hellow this is a thread");
        dashboardController.updateMoviesOnDashboard();
        dashboardController.updateSideMovieOnDashboard();

    }
}
