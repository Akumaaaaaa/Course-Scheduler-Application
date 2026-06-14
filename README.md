# Course Schedule

An Android application for managing and tracking university course schedules, with daily reminders and dark mode support.

## Features

- **Home Dashboard** — Displays the nearest upcoming course with a countdown timer. Automatically falls back to the next available day if no course is scheduled today.
- **Course List** — Paginated list of all courses with swipe-to-delete and sort by time, course name, or lecturer.
- **Add Course** — Form to add a new course with day selection, time pickers, lecturer, and notes.
- **Course Detail** — View full course details and delete with confirmation.
- **Daily Reminder** — AlarmManager-based notification at 6:00 AM showing today's course schedule in an inbox-style notification.
- **Dark Mode** — Toggle between light, dark, and system-default themes from Settings.
- **Boot Persistence** — Alarm is automatically re-registered after device reboot if notifications are enabled.

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| Architecture | MVVM + Repository |
| Database | Room (SQLite) |
| Async/Threading | SingleThreadExecutor + LiveData |
| UI | Material Design 3, ConstraintLayout, RecyclerView + Paging 2 |
| Notifications | AlarmManager + NotificationCompat |
| Preferences | AndroidX PreferenceFragmentCompat |
| Build | Gradle 7.3.3, KAPT |

## Requirements

- Android 5.0 (API 21) or higher
- Target SDK: Android 12 (API 32)

## Getting Started

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Open the project in Android Studio (Arctic Fox or newer recommended).
3. Let Gradle sync and download dependencies.
4. Run on a device or emulator running Android 5.0+.

## Project Structure

```
app/src/main/java/com/dicoding/courseschedule/
├── data/
│   ├── Course.kt              # Room entity
│   ├── CourseDao.kt           # Database access object
│   ├── CourseDatabase.kt      # Room database singleton
│   └── DataRepository.kt     # Single source of truth
├── notification/
│   ├── DailyReminder.kt       # BroadcastReceiver + AlarmManager scheduling
│   └── BootReceiver.kt        # Re-registers alarm after device reboot
├── paging/
│   ├── CourseAdapter.kt       # PagedListAdapter
│   └── CourseViewHolder.kt    # RecyclerView item holder
├── ui/
│   ├── home/                  # Home screen (nearest schedule)
│   ├── list/                  # All courses list
│   ├── add/                   # Add course form
│   ├── detail/                # Course detail & delete
│   └── setting/               # Settings (theme + notifications)
└── util/
    ├── Constant.kt            # Notification constants, thread executor
    ├── DayName.kt             # Day enum mapping
    ├── Event.kt               # Single-event LiveData wrapper
    ├── NightMode.kt           # Dark mode enum
    ├── QueryUtil.kt           # SQL query builder for sorting/nearest schedule
    ├── SortType.kt            # Sort options enum
    ├── TimePickerFragment.kt  # Time picker dialog
    └── TimeUtil.kt            # Relative time formatting
```

## Database Schema

**Table: `course`**

| Column | Type | Description |
|---|---|---|
| `id` | INTEGER (PK) | Auto-generated primary key |
| `courseName` | TEXT | Name of the course |
| `day` | INTEGER | Day of week (1=Sunday … 7=Saturday, matching `Calendar.DAY_OF_WEEK`) |
| `startTime` | TEXT | Start time in `HH:MM` format |
| `endTime` | TEXT | End time in `HH:MM` format |
| `lecturer` | TEXT | Lecturer name |
| `note` | TEXT | Additional notes |

## Notifications

- The daily reminder fires at **6:00 AM** every day.
- It shows today's courses as an inbox-style notification.
- Enable/disable via **Settings → Notification**.
- The alarm is automatically restored after device reboot.
- On Android 13+ (`POST_NOTIFICATIONS` permission), the user must grant notification permission when prompted.

## Settings

| Setting | Options | Default |
|---|---|---|
| Notification | On / Off | Off |
| Dark Mode | Automatic / Dark / Light | Automatic |

## Known Limitations

- Uses Paging Library 2 (`PagedListAdapter`); migration to Paging 3 is recommended for new development.
- The `WorkManager` dependency is declared but not used; it can be removed if no background sync is needed.
- No input length validation on course name, lecturer, or note fields.
