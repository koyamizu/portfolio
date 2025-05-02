
function initializeCalendar(events) {
	let calendarEl = document.getElementById('calendar');
	let calendar = new FullCalendar.Calendar(calendarEl, {
		googleCalendarApiKey: 'AIzaSyC5jAdnxhwc9qBhBNB-xT-p8tD-tn6LuQ0',
		contentHeight: "auto",
		selectable: true,
		eventSources: [{
			url: '/api/event/all',
		},
		{
			googleCalendarId: 'ja.japanese#holiday@group.v.calendar.google.com',
			className: 'holiday',
		}],
		displayEventTime: false,
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
		        }
		      });
		    },
		    eventMouseLeave: function(info) {
		      const title = info.event.title;
		      document.querySelectorAll('.fc-event').forEach(evtEl => {
		        const t = evtEl.querySelector('.fc-event-title');
		        if (t && t.innerText === title) {
		          evtEl.classList.remove('highlight');
		        }
		      });
		    }
	});
	calendar.render();
}