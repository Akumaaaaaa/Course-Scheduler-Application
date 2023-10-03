package couseschedule

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.courseschedule.ui.list.ListActivity
import com.dicoding.courseschedule.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ListActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(ListActivity::class.java)

    @Test
    fun testAddCourseMenu_Click_ShouldOpenAddCourseActivity() {
        // Click on the Add Course (+) Menu
        onView(withId(R.id.fab)).perform(click())

        // Check if the AddCourseActivity is displayed
        onView(withId(R.id.et_course_name)).check(matches(isDisplayed()))
    }
}
