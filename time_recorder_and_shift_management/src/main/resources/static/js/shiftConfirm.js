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
			{ events }
		],
		locale: 'ja',
		eventDidMount: function(e) {
			let el = e.el;
			//普通のイベントとわけて考えるため、条件分岐。
			if (el.classList.contains('holiday')) {
				if (e.view.type == "dayGridMonth") { //カレンダー(月)表示の場合
					//イベントが表示される場所の親をたどって各日の枠にたどり着いたらclassを授けよう
					el.closest('.fc-daygrid-day').classList.add('is_holiday');
				}
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
}
