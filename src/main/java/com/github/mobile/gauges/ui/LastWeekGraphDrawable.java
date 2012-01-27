package com.github.mobile.gauges.ui;

import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.SATURDAY;
import static java.util.Calendar.SUNDAY;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.PaintDrawable;

import com.github.mobile.gauges.core.DatedViewSummary;
import com.github.mobile.gauges.core.ViewSummary;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Drawable of a bar graph of people and views over a 7-day period
 */
public class LastWeekGraphDrawable extends PaintDrawable {

	private static final int COLOR_VIEWS_WEEKDAY = Color.parseColor("#53685E");

	private static final int COLOR_VIEWS_WEEKEND = Color.parseColor("#626262");

	private static final int COLOR_PEOPLE_WEEKDAY = Color.parseColor("#6E9180");

	private static final int COLOR_PEOPLE_WEEKEND = Color.parseColor("#7C7C7C");

	private static final int MIN_HEIGHT = 4;

	private static final int SPACING_X = 2;

	private DatedViewSummary[] entries;

	private long max = 1;

	/**
	 * Create drawable for view summaries
	 *
	 * @param entries
	 *            last week of entries
	 */
	public LastWeekGraphDrawable(DatedViewSummary[] entries) {
		this.entries = entries;
		for (ViewSummary entry : entries)
			max = Math.max(max, Math.max(entry.getPeople(), entry.getViews()));
	}

	@Override
	public void draw(Canvas canvas) {
		Paint paint = getPaint();
		Rect bound = getBounds();
		int x = 0;
		int width = bound.width() / entries.length;
		width -= SPACING_X;
		int height = bound.height();
		Calendar calendar = new GregorianCalendar();
		calendar.add(DAY_OF_WEEK, -6);
		for (DatedViewSummary entry : entries) {
			long views = 0;
			long people = 0;
			if (entry != null) {
				views = entry.getViews();
				people = entry.getPeople();
			}

			int dayOfWeek = calendar.get(DAY_OF_WEEK);
			if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY)
				paint.setColor(COLOR_VIEWS_WEEKEND);
			else
				paint.setColor(COLOR_VIEWS_WEEKDAY);

			float percentage = (float) views / max;
			canvas.drawRect(x,
					height - Math.max(MIN_HEIGHT, percentage * height), x
							+ width, bound.bottom, paint);

			if (dayOfWeek == SATURDAY || dayOfWeek == SUNDAY)
				paint.setColor(COLOR_PEOPLE_WEEKEND);
			else
				paint.setColor(COLOR_PEOPLE_WEEKDAY);

			percentage = (float) people / max;
			canvas.drawRect(x,
					height - Math.max(MIN_HEIGHT, percentage * height), x
							+ width, bound.bottom, paint);

			calendar.add(DAY_OF_YEAR, 1);
			x += width + SPACING_X;
		}
	}
}