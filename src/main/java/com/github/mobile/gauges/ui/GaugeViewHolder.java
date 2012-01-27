package com.github.mobile.gauges.ui;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mobile.gauges.R.id;
import com.github.mobile.gauges.core.DatedViewSummary;
import com.github.mobile.gauges.core.Gauge;
import com.madgag.android.listviews.ViewHolder;

import java.text.MessageFormat;

/**
 * View holder for a {@link Gauge}
 */
public class GaugeViewHolder implements ViewHolder<Gauge> {

	private final TextView nameText;

	private final TextView viewsText;

	private final TextView peopleText;

	private final LinearLayout barGraph;

	/**
	 * Create view holder
	 *
	 * @param view
	 */
	public GaugeViewHolder(final View view) {
		nameText = (TextView) view.findViewById(id.tv_gauge_name);
		viewsText = (TextView) view.findViewById(id.tv_gauge_views);
		peopleText = (TextView) view.findViewById(id.tv_gauge_people);
		barGraph = (LinearLayout) view.findViewById(id.ll_bars);
	}

	public void updateViewFor(final Gauge gauge) {
		nameText.setText(gauge.getTitle());
		viewsText.setText(MessageFormat.format("{0}", gauge.getToday()
				.getViews()));
		peopleText.setText(MessageFormat.format("{0}", gauge.getToday()
				.getPeople()));
		DatedViewSummary[] lastWeek = new DatedViewSummary[7];
		int index = lastWeek.length - 1;
		for (DatedViewSummary day : gauge.getRecentDays()) {
			lastWeek[index--] = day;
			if (index < 0)
				break;
		}
		barGraph.setBackgroundDrawable(new LastWeekGraphDrawable(lastWeek));
	}
}