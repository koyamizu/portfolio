function initializeCalendar(events) {
	const today = new Date();

	// 翌月の 1 日
	const nextMonthFirst = new Date(today.getFullYear(), today.getMonth() + 1, 1);
	// 翌月の末日
	const nextMonthLast = new Date(today.getFullYear(), today.getMonth() + 2, 1);

	const calendarEl = document.getElementById('calendar');

	const calendar = new FullCalendar.Calendar(calendarEl, {
		googleCalendarApiKey: 'AIzaSyC5jAdnxhwc9qBhBNB-xT-p8tD-tn6LuQ0',
		initialView: 'dayGridMonth',
		eventClick: function(info) {
			info.jsEvent.preventDefault();
		},
		eventSources: [
			{
				googleCalendarId: 'ja.japanese#holiday@group.v.calendar.google.com',
				className: 'holiday',
			},
			{
				events,
				className: 'requestList'
			}
		],
		dayCellContent:e=>e.dayNumberText=e.dayNumberText.replace('日',''),
		locale: 'ja',
		eventDidMount: function(e) {
			let el = e.el;
			if (el.classList.contains('requestList')) {
				el.closest('.fc-daygrid-day').classList.add('confirm');
			}
		},
		// 1) 最初に開く日を翌月の１日に
		initialDate: nextMonthFirst,

		// 2) 表示範囲を翌月の 1 日 ～ 翌々月 1 日（排他＝翌月末）に
		validRange: {
			start: nextMonthFirst,
			end: nextMonthLast
		},
		selectable: true,
		contentHeight: 'auto',

	});

	calendar.render();

//	function toggleWeekdayColumn(wd, checked) {
//		const cur = calendar.getDate();      // 表示中の年月を取得
//		const year = cur.getFullYear();
//		const month = cur.getMonth();
//
//		// その月の1日～最終日のループ
//		const first = new Date(year, month, 2);
//		const last = new Date(year, month + 1, 1);
//		for (let d = new Date(first); d <= last; d.setDate(d.getDate() + 1)) {
//			if (d.getDay() !== wd) continue;
//			const ds = d.toISOString().slice(0, 10); // "YYYY-MM-DD"
//			const cell = document.querySelector(`td[data-date="${ds}"]`);
//			if (!cell) continue;
//
//			if (checked) {
//				if (!selectedDates.includes(ds)) {
//					selectedDates.push(ds);
//					cell.classList.add('selected');
//				}
//			} else {
//				const i = selectedDates.indexOf(ds);
//				if (i > -1) {
//					selectedDates.splice(i, 1);
//					cell.classList.remove('selected');
//				}
//			}
//		}
//		console.log('After toggleWeekdayColumn:', selectedDates);
//	}
}
