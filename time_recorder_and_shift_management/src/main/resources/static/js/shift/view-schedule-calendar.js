
function initializeCalendar(events) {
	let calendarEl = document.getElementById('calendar');
//	let elementToday=document.querySelector('td.fc-day-today');
	let calendar = new FullCalendar.Calendar(calendarEl, {
		googleCalendarApiKey: 'AIzaSyC5jAdnxhwc9qBhBNB-xT-p8tD-tn6LuQ0',
		contentHeight: "auto",
		selectable: true,
		eventSources: [{
			events,
		},
		{
			googleCalendarId: 'ja.japanese#holiday@group.v.calendar.google.com',
			className: 'holiday',
		}],
		displayEventTime: false,
		businessHours: true,
		locale: 'ja',
		eventDidMount: function(e) {
			let el = e.el;
			if (el.classList.contains('holiday')) {
				if (e.view.type == "dayGridMonth") {
					el.closest('.fc-daygrid-day').classList.add('is_holiday');
				}
			}
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
		dayCellContent:e=>e.dayNumberText=e.dayNumberText.replace('日','')
	});
	calendar.render();
}
