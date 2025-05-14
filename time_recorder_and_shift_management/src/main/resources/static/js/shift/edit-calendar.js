//元ネタはshiftSchedule.js
function initializeCalendar(events) {
	const today = new Date();

	// 翌月の 1 日
	const nextMonthFirst = new Date(today.getFullYear(), today.getMonth() + 1, 1);
	// 翌月の末日
	const nextMonthLast = new Date(today.getFullYear(), today.getMonth() + 2, 1);

	let selectedDates = [];
	let calendarEl = document.getElementById('calendar');
	const form = document.getElementById('shift-form');
	const hiddenInput = document.getElementById('selectedDatesInput');

	let calendar = new FullCalendar.Calendar(calendarEl, {
		googleCalendarApiKey: 'AIzaSyC5jAdnxhwc9qBhBNB-xT-p8tD-tn6LuQ0',
		contentHeight: "auto",
		initialView: 'dayGridMonth',
		eventClick: function(info) {
			info.jsEvent.preventDefault();
		},
		eventSources: [{
			events
		},
		{
			googleCalendarId: 'ja.japanese#holiday@group.v.calendar.google.com',
			className: 'holiday',
		}],
		displayEventTime: false,
		businessHours: true,
		locale: 'ja',
		selectable: true,
		contentHeight: 'auto',
		eventDidMount: function(e) {
			let el = e.el;
			if (el.classList.contains('holiday')) {
				el.closest('.fc-daygrid-day').classList.add('is_holiday');
			}
		},
		// 1) 最初に開く日を翌月の１日に
		initialDate: nextMonthFirst,

		// 2) 表示範囲を翌月の 1 日 ～ 翌々月 1 日（排他＝翌月末）に
		validRange: {
			start: nextMonthFirst,
			end: nextMonthLast
		},

		eventMouseEnter: function(info) {
			const title = info.event.title;
			document.querySelectorAll('.fc-event').forEach(evtEl => {
				const t = evtEl.querySelector('.fc-event-title');
				if (t && t.innerText === title) {
					evtEl.classList.add('highlight');
				} else {
					evtEl.classList.add('lowlight');
				}
			});
		},
		eventMouseLeave: function(info) {
			const title = info.event.title;
			document.querySelectorAll('.fc-event').forEach(evtEl => {
				const t = evtEl.querySelector('.fc-event-title');
				if (t && t.innerText === title) {
					evtEl.classList.remove('highlight');
				} else {
					evtEl.classList.remove('lowlight');
				}
			});
		},
		dateClick: info => {
			toggleDate(info.dateStr, info.dayEl);
		},
	});
	calendar.render();

	form.addEventListener('submit', function(e) {
		selectedDates.sort();
		// 例: ["2025-04-01","2025-04-08",…] の形
		hiddenInput.value = JSON.stringify(selectedDates);
		// （特に e.preventDefault は不要。値セット後 自然送信。）
	});

	function toggleDate(dateStr, cellEl) {
		const idx = selectedDates.indexOf(dateStr);
		if (idx > -1) {
			selectedDates.splice(idx, 1);
			cellEl.classList.remove('selected');
		} else {
			selectedDates.push(dateStr);
			cellEl.classList.add('selected');
		}
		console.log('Selected dates:', selectedDates);
	}
}