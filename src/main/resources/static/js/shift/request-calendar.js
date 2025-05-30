function initializeCalendar(employeeId, events) {
	const today = new Date();

	// 翌月の 1 日
	const nextMonthFirst = new Date(today.getFullYear(), today.getMonth() + 1, 1);
	// 翌月の末日
	const nextMonthLast = new Date(today.getFullYear(), today.getMonth() + 2, 1);

	let selectedDates = (events==null)?[]:events;
	const calendarEl = document.getElementById('calendar');
	const clearBtn = document.getElementById('clear-selection');
	const form = document.getElementById('shift-form');
	const hiddenInput = document.getElementById('selectedDatesInput');

	// （ここまで既存のカレンダー初期化や toggleDate, injectColumnCheckboxes, …）

	const calendar = new FullCalendar.Calendar(calendarEl, {
		googleCalendarApiKey: 'AIzaSyC5jAdnxhwc9qBhBNB-xT-p8tD-tn6LuQ0',
		initialView: 'dayGridMonth',
		eventClick: function(info) {
			info.jsEvent.preventDefault();
			//			showRegistrationModal;
		},
		eventSources: [
			{
				googleCalendarId: 'ja.japanese#holiday@group.v.calendar.google.com',
				className: 'holiday'
			},
			//			{
			//				//「events」という名前じゃないと認識されないっぽい
			//				events,
			//				className: 'request'
			//			}
		],
		locale: 'ja',
		eventDidMount: function(e) {
			let el = e.el;
			if (el.classList.contains('holiday')) {
				el.closest('.fc-daygrid-day').classList.add('is_holiday');
			}
			if (el.classList.contains('request')) {
				el.closest('.fc-daygrid-day').classList.add('selected');
			}
			//				if (e.view.type == "dayGridMonth") { //カレンダー(月)表示の場合
			//					//イベントが表示される場所の親をたどって各日の枠にたどり着いたらclassを授けよう
			//					el.closest('.fc-daygrid-day').classList.add('is_holiday');
			//				}
		},
		dayCellContent: e => e.dayNumberText = e.dayNumberText.replace('日', ''),
		// 1) 最初に開く日を翌月の１日に
		initialDate: nextMonthFirst,

		// 2) 表示範囲を翌月の 1 日 ～ 翌々月 1 日（排他＝翌月末）に
		validRange: {
			start: nextMonthFirst,
			end: nextMonthLast
		},
		selectable: true,
		contentHeight: 'auto',
		// 日付クリックで個別トグル
		dateClick: info => {
			toggleDate(info.dateStr, info.dayEl);
		},
		eventClick: info => {
			toggleDate(info.event._def, info.el);
		},
		// 月が変わるたびにチェックボックス列を再構築
		datesSet: injectColumnCheckboxes,
	});

	calendar.render();
	
	addClassSelected(selectedDates);

	clearBtn.addEventListener('click', clearAllSelections);

	// フォーム送信時に selectedDates を JSON 文字列化して隠しフィールドにセット
	form.addEventListener('submit', function(e) {
		selectedDates.sort((a, b) => {
			//-1はそのまま　0が変更
			return (a.start < b.start) ? -1 : 0
		});
		console.log('Selected dates:', selectedDates);
		// 例: ["2025-04-01","2025-04-08",…] の形
		hiddenInput.value = JSON.stringify(selectedDates);
		// （特に e.preventDefault は不要。値セット後 自然送信。）
	});

	// --- 関数定義 ---
	// 個別日付トグル
	function toggleDate(dateStr, cellEl) {
		//		let dates = selectedDates.map(s => s.start);
		const idx = selectedDates.map(s => s.start).indexOf(dateStr);
		//		const idx = dates.indexOf(dateStr);
		//		indexOf({
		//			employeeId: employeeId,
		//			start: dateStr
		//		});
		if (idx > -1) {
			selectedDates.splice(idx, 1);
			cellEl.classList.remove('selected');
		} else {
			selectedDates.push({
				employeeId: employeeId,
				start: dateStr,
				scheduledStart: '06:00',
				scheduledEnd: '09:00'
			});
			cellEl.classList.add('selected');
		}
		console.log('Selected dates:', selectedDates);
	}

	function addClassSelected(sd) {
		const cur = calendar.getDate();      // 表示中の年月を取得
		const year = cur.getFullYear();
		const month = cur.getMonth();

		// その月の1日～最終日のループ
		const first = new Date(year, month, 2);
		const last = new Date(year, month + 1, 1);
		let cnt = 0;
		for (let d = new Date(first); d <= last && cnt < sd.length; d.setDate(d.getDate() + 1)) {
			const ds = d.toISOString().slice(0, 10); // "YYYY-MM-DD"
			if (ds != sd[cnt].start) continue;
			const cell = document.querySelector(`td[data-date="${ds}"]`);
			cell.classList.add('selected');
			cnt++;
		}
	}

	// ヘッダーセルにチェックボックスを埋め込む
	function injectColumnCheckboxes() {
		// 既存のチェックボックスを削除
		calendarEl.querySelectorAll('.col-checkbox').forEach(cb => cb.remove());

		// 「日」「月」…のヘッダーセルをループ
		const headers = calendarEl.querySelectorAll('.fc-col-header-cell-cushion');
		headers.forEach((headerElem, idx) => {
			const cb = document.createElement('input');
			cb.type = 'checkbox';
			cb.className = 'col-checkbox';
			// セル先頭に追加
			headerElem.insertAdjacentElement('afterbegin', cb);

			cb.addEventListener('change', () => {
				// 「1つ右」の曜日を計算 (0→1, 1→2, …, 6→0)
				const targetDay = (idx + 1) % 7;
				toggleWeekdayColumn(targetDay, cb.checked);
			});
		});
	}

	// 指定曜日の列をまとめてトグル
	function toggleWeekdayColumn(wd, checked) {
		const cur = calendar.getDate();      // 表示中の年月を取得
		const year = cur.getFullYear();
		const month = cur.getMonth();

		// その月の1日～最終日のループ
		const first = new Date(year, month, 2);
		const last = new Date(year, month + 1, 1);
		for (let d = new Date(first); d <= last; d.setDate(d.getDate() + 1)) {
			if (d.getDay() !== wd) continue;
			const ds = d.toISOString().slice(0, 10); // "YYYY-MM-DD"
			const cell = document.querySelector(`td[data-date="${ds}"]`);
			if (!cell) continue;

			const idx = selectedDates.map(s => s.start).indexOf(ds);

			//			const target = {
			//				employeeId: employeeId,
			//				start: ds
			//			};
			if (checked) {

				if (idx < 0) {
					selectedDates.push({
						employeeId: employeeId,
						start: ds,
						scheduledStart: '06:00',
						scheduledEnd: '09:00'
					})
					cell.classList.add('selected');
				}
			} else {
				if (idx > -1) {
					selectedDates.splice(idx, 1);
					cell.classList.remove('selected');
				}
				//				else {
				//					selectedDates.push({
				//						employeeId: employeeId,
				//						start: ds
				//					});
				//					cell.classList.add('selected');
				//				}
			}

		} console.log('After toggleWeekdayColumn:', selectedDates);
	}

	//	function showRegistrationModal() {
	//		let modal = document.getElementById("modal");
	//		if (modal) {
	//			modal.style.display = 'flex';
	//		}
	//	}
	//
	//	function closeRegistrationModal() {
	//		let modal = document.getElementById('modal');
	//		if (modal) {
	//			modal.style.display = 'none';
	//		}
	//	}
	function clearAllSelections() {
		selectedDates.length = 0;
		document.querySelectorAll('td.selected').forEach(cell => cell.classList.remove('selected'));
		document.querySelectorAll('.col-checkbox').forEach(cb => cb.checked = false);
		console.log('All selections cleared');
	}
}
