package ThreadClasses;


import apiKeys.Services;
import sample.DashboardController;

public class RenderDashboardMovies implements Runnable{
    DashboardController dashboardController;
    private int Current_Pg = 1;

    public RenderDashboardMovies(DashboardController dashboardController){
        this.dashboardController = dashboardController;
    }
    @Override
    public void run() {
        boolean adult = false;
        Services serviceObject = null;
        final String mykey = serviceObject.API_KEY;
        dashboardController.updateMoviesOnDashboard("https://api.themoviedb.org/3/discover/movie?api_key=" + mykey + "&language=en-US"
                + "&include_adult=" + adult + "&page="+Current_Pg);
        dashboardController.updateSideMovieOnDashboard();

    }
}
