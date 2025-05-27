//元ネタはshiftSchedule.js
function initializeCalendar(events) {
	const today = new Date();

	// 翌月の 1 日
	const nextMonthFirst = new Date(today.getFullYear(), today.getMonth() + 1, 1);
	// 翌月の末日
	const nextMonthLast = new Date(today.getFullYear(), today.getMonth() + 2, 1);

	let selectedShifts = events.map(e => {
		return {
			id: e.id,
			start: e.start,
			scheduledStart:e.scheduledStart,
			scheduledEnd:e.scheduledEnd,
			employeeId: e.employeeId,
			title: e.title
		}
	})
//	オートインクリメントの次の値を取得する？
	let nextShiftId = selectedShifts[selectedShifts.length - 1].id + 1;
	let calendarEl = document.getElementById('calendar');
	const form = document.getElementById('shift-form');
	const hiddenInput = document.getElementById('selectedDatesInput');

	let containerEl = document.getElementById('external-events');

	new FullCalendar.Draggable(containerEl, {
		itemSelector: '.fc-event-main',
		eventData: function(eventEl) {
			return {
//				id: nextShiftId++,
				employeeId: eventEl.id,
				title: eventEl.innerText,
				editable: true
			};
		},
	});

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
		droppable: true,
		//		editable:true,
		contentHeight: 'auto',
		eventDidMount: function(e) {
			let el = e.el;
			if (el.classList.contains('holiday')) {
				el.closest('.fc-daygrid-day').classList.add('is_holiday');
			}
			if (el.classList.contains('request')) {
				el.closest('.fc-daygrid-day').classList.add('selected');
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
			if (info.el.classList.contains('fc-event-draggable')) {
				info.event.remove();
				const idx = selectedShifts.findIndex(s => {
					return s.employeeId==parseInt(info.event.extendedProps.employeeId, 10)
					 && s.start==info.event.startStr
//					return s.id == info.event._def.publicId
				});
				selectedShifts.splice(idx, 1);
			} else {
				toggleDate(info.event._def, info.el);
			}
		},
		eventReceive: info => {
			const cell = document.querySelector(`td[data-date="${info.event.startStr}"]`);
			const members = cell.getElementsByClassName('fc-event-title fc-sticky');
			let duplicate = 0;
			for (i = 0; i < members.length; i++) {
				if (members[i].innerText == info.event.title) {
					duplicate++;
				}
			}
			if (duplicate > 1) {
				info.event.remove();
			} else {
				selectedShifts.push({
					id: parseInt(info.event._def.publicId, 10),
					start: info.event.startStr,
					scheduledStart:'06:00',
					scheduledEnd:'09:00',
					employeeId: parseInt(info.event.extendedProps.employeeId, 10),
					//					title: info.event.title
				})
				console.log('Selected dates:', selectedShifts);
			}
		},
		dayCellContent: e => e.dayNumberText = e.dayNumberText.replace('日', '')
	});
	calendar.render();

	function toggleDate(request, evtEl) {
		const idx = selectedShifts.findIndex(s => {
			return s.id == request.publicId
		});

		if (idx > -1) {
			selectedShifts.splice(idx, 1);
			evtEl.style.backgroundColor = 'rgb(178,183,181)';
			evtEl.style.borderColor = 'rgb(178,183,181)';
			evtEl.firstChild.style.color = 'rgb(92,92,92)';
		} else {
			shift = events.find(e => {
				return e.id = request.publicId
			});
			selectedShifts.push({
				id: parseInt(shift.id, 10),
				start: shift.start,
				scheduledStart:'06:00',
				scheduledEnd:'09:00',
				employeeId: shift.employeeId,
			});
			evtEl.style.backgroundColor = '#02e09a';
			evtEl.style.borderColor = '#02e09a';
			evtEl.firstChild.style.color = '#006666';
		}
		console.log('Selected dates:', selectedShifts);
	}

	form.addEventListener('submit', function(e) {
		selectedShifts.sort((a, b) => {
			//-1はそのまま　0が変更
			return (a.start < b.start) ? -1 : 0
		});
		// 例: ["2025-04-01","2025-04-08",…] の形
		hiddenInput.value = JSON.stringify(selectedShifts);
		// （特に e.preventDefault は不要。値セット後 自然送信。）
	});

}