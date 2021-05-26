package com.dicoding.naufal.mymoviecatalog.ui.favorite

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.dicoding.naufal.mymoviecatalog.R
import com.dicoding.naufal.mymoviecatalog.data.source.local.entity.ShowEntity
import com.dicoding.naufal.mymoviecatalog.ui.home.HomeActivity
import com.dicoding.naufal.mymoviecatalog.utils.DataDummy
import com.dicoding.naufal.mymoviecatalog.utils.EspressoIdlingResources
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeActivityTest{
    private val dummyShows = DataDummy.generateDummyShow()

    @Before
    fun setUp(){
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource)
    }

    @Test
    fun loadMovies(){
        val dummyMovies = ArrayList<ShowEntity>()
        for (showEntity in DataDummy.generateDummyShow()){
            if (showEntity.isMovie){
                dummyMovies.add(showEntity)
            }
        }
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadSeries(){
        val dummySeries = ArrayList<ShowEntity>()
        for (showEntity in DataDummy.generateDummyShow()){
            if (!showEntity.isMovie){
                dummySeries.add(showEntity)
            }
        }
        onView(withId(R.id.navigation_series)).perform(click())
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummySeries.size))
    }

    @Test
    fun loadDetailMovies(){
        val dummyMovies = ArrayList<ShowEntity>()
        for (showEntity in DataDummy.generateDummyShow()){
            if (showEntity.isMovie){
                dummyMovies.add(showEntity)
            }
        }
        onView(withId(R.id.navigation_movies)).perform(click())
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_content)).check(matches(withText(dummyMovies[0].showTitle)))
        onView(withId(R.id.tv_date_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_content)).check(matches(withText(dummyMovies[0].showReleaseDate)))
        onView(withId(R.id.tv_writer_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_writer_content)).check(matches(withText(dummyMovies[0].showWriter)))
        onView(withId(R.id.tv_genre_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre_content)).check(matches(withText(dummyMovies[0].showGenre)))
        onView(withId(R.id.tv_sinopsys_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_sinopsys_content)).check(matches(withText(dummyMovies[0].showSinopsys)))
        onView(withId(R.id.tv_stars_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_stars_content)).check(matches(withText(dummyMovies[0].showStars)))

    }

    @Test
    fun loadDetailSeries(){
        val dummySeries = ArrayList<ShowEntity>()
        for (showEntity in DataDummy.generateDummyShow()){
            if (!showEntity.isMovie){
                dummySeries.add(showEntity)
            }
        }
        onView(withId(R.id.navigation_series)).perform(click())
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_content)).check(matches(withText(dummySeries[0].showTitle)))
        onView(withId(R.id.tv_date_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date_content)).check(matches(withText(dummySeries[0].showReleaseDate)))
        onView(withId(R.id.tv_writer_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_writer_content)).check(matches(withText(dummySeries[0].showWriter)))
        onView(withId(R.id.tv_genre_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genre_content)).check(matches(withText(dummySeries[0].showGenre)))
        onView(withId(R.id.tv_sinopsys_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_sinopsys_content)).check(matches(withText(dummySeries[0].showSinopsys)))
        onView(withId(R.id.tv_stars_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_stars_content)).check(matches(withText(dummySeries[0].showStars)))
    }

    @Test
    fun emptyFavoriteMovies(){
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("MOVIES")).perform(click())
        onView(allOf(withId(R.id.image_empty), isCompletelyDisplayed())).check(matches(isDisplayed()))
    }

    @Test
    fun emptyFavoriteSeries(){
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("TV SERIES")).perform(click())
        onView(allOf(withId(R.id.image_empty), isCompletelyDisplayed())).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteMovies(){
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("MOVIES")).perform(click())
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_writer_content)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    @Test
    fun loadFavoriteSeries(){
        onView(withId(R.id.navigation_series)).perform(click())
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("TV SERIES")).perform(click())
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.rv_show), isCompletelyDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tv_title_content)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_writer_content)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }
}