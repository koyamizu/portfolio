function initializeCalendar() {
	const today = new Date();

	// 翌月の 1 日
	const nextMonthFirst = new Date(today.getFullYear(), today.getMonth() + 1, 1);
	// 翌月の末日
	const nextMonthLast = new Date(today.getFullYear(), today.getMonth() + 2, 1);

	let selectedDates = [];
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
		},
		events: {
			googleCalendarId: 'ja.japanese#holiday@group.v.calendar.google.com',
			className: 'holiday',
		},
		locale: 'ja',
		eventDidMount: function(e) {
			let el = e.el;
			if (el.classList.contains('holiday')) {
					el.closest('.fc-daygrid-day').classList.add('is_holiday');
				}
//				if (e.view.type == "dayGridMonth") { //カレンダー(月)表示の場合
//					//イベントが表示される場所の親をたどって各日の枠にたどり着いたらclassを授けよう
//					el.closest('.fc-daygrid-day').classList.add('is_holiday');
//				}
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
		// 日付クリックで個別トグル
		dateClick: info => {
			toggleDate(info.dateStr, info.dayEl);
		},
		// 月が変わるたびにチェックボックス列を再構築
		datesSet: injectColumnCheckboxes,

	});

	calendar.render();

	clearBtn.addEventListener('click', clearAllSelections);

	// フォーム送信時に selectedDates を JSON 文字列化して隠しフィールドにセット
	form.addEventListener('submit', function(e) {
		selectedDates.sort();
		// 例: ["2025-04-01","2025-04-08",…] の形
		hiddenInput.value = JSON.stringify(selectedDates);
		// （特に e.preventDefault は不要。値セット後 自然送信。）
	});

	// --- 関数定義 ---

	// 個別日付トグル
	function toggleDate(dateStr, cellEl) {
		const idx = selectedDates.indexOf(dateStr);
		if (idx > -1) {
			selectedDates.splice(idx, 1);
			cellEl.classList.remove('selected');
		} else {
			selectedDates.push({
				employee_id:/*[[${shiftRequestForm.employeeId}]]*/null,
				date:dateStr
			});
			cellEl.classList.add('selected');
		}
		console.log('Selected dates:', selectedDates);
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

			if (checked) {
				if (!selectedDates.includes(ds)) {
					selectedDates.push(ds);
					cell.classList.add('selected');
				}
			} else {
				const i = selectedDates.indexOf(ds);
				if (i > -1) {
					selectedDates.splice(i, 1);
					cell.classList.remove('selected');
				}
			}
		}
		console.log('After toggleWeekdayColumn:', selectedDates);
	}

	function clearAllSelections() {
		selectedDates.length = 0;
		document.querySelectorAll('td.selected').forEach(cell => cell.classList.remove('selected'));
		document.querySelectorAll('.col-checkbox').forEach(cb => cb.checked = false);
		console.log('All selections cleared');
	}
}
