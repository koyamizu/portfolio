//元ネタはshiftSchedule.js
function initializeCalendar(events) {
	const today = new Date();

	// 翌月の 1 日
	const nextMonthFirst = new Date(today.getFullYear(), today.getMonth() + 1, 1);
	// 翌月の末日
	const nextMonthLast = new Date(today.getFullYear(), today.getMonth() + 2, 1);

	let selectedShifts = [];
	for (let i = 0; i < events.length; i++) {
		selectedShifts[i] = {
			id: events[i].id,
			title: events[i].title,
			start: events[i].start
		}
	}

	let calendarEl = document.getElementById('calendar');
	const form = document.getElementById('shift-form');
	const hiddenInput = document.getElementById('selectedDatesInput');
	
//	let containerEl = document.getElementById('external-events');
//
//	new FullCalendar.Draggable(containerEl, {
//		itemSelector: '.fc-event',
//		eventData: function(eventEl) {
//			return {
//				title: eventEl.innerText
//			};
//		},
//	});

	let calendar = new FullCalendar.Calendar(calendarEl, {
		googleCalendarApiKey: 'AIzaSyC5jAdnxhwc9qBhBNB-xT-p8tD-tn6LuQ0',
		contentHeight: "auto",
		initialView: 'dayGridMonth',
		//		eventClick: function(info) {
		//			info.jsEvent.preventDefault();
		//		},
		eventSources: [{
			events,
			className: 'schedule-edit'
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
		eventClick: info => {
			toggleDate(info.event._def, info.el);
		},
	});
	calendar.render();

	function toggleDate(request, evtEl) {
		const idx = selectedShifts.findIndex(s => {
			return s.id == request.publicId
		});

		if (idx > -1) {
			selectedShifts.splice(idx, 1);
			//			evtEl.classList.add('un-selected');
			evtEl.style.backgroundColor = 'rgb(178,183,181)';
			evtEl.style.borderColor = 'rgb(178,183,181)';
			evtEl.firstChild.style.color = 'rgb(92,92,92)';
		} else {
			shift = events.find(e => {
				return e.id = request.publicId
			});
			selectedShifts.push({ id: shift.id, title: shift.title, start: shift.start });
			evtEl.style.backgroundColor = '#02e09a';
			evtEl.style.borderColor = '#02e09a';
			evtEl.firstChild.style.color = '#006666';
		}
		console.log('Selected dates:', selectedShifts);
	}

	form.addEventListener('submit', function(e) {
		selectedShifts.sort();
		// 例: ["2025-04-01","2025-04-08",…] の形
		hiddenInput.value = JSON.stringify(selectedShifts);
		// （特に e.preventDefault は不要。値セット後 自然送信。）
	});

}