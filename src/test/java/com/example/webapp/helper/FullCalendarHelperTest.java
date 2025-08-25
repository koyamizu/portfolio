package com.example.webapp.helper;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.webapp.entity.FullCalendarDisplay;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.form.FullCalendarForm;
import com.example.webapp.test_data.ShiftManagementTestDataGenerator;
import com.example.webapp.test_data.employee.EMPLOYEE;

public class FullCalendarHelperTest {

	@Test
	void test_setColorProperties() {
		List<FullCalendarEntity> targets = ShiftManagementTestDataGenerator.getAprilSchedules();
		List<FullCalendarDisplay> targetsDisplay = FullCalendarHelper.convertFullCalendarDisplays(targets);
		FullCalendarHelper.setColorProperties("#02e09a", "#006666", targetsDisplay);
		for (int cnt = 0; cnt < targetsDisplay.size(); cnt++) {
			assertThat(targetsDisplay).extracting(FullCalendarDisplay::getBackgroundColor)
					.contains(targetsDisplay.get(cnt).getBackgroundColor());
			assertThat(targetsDisplay).extracting(FullCalendarDisplay::getBorderColor)
					.contains(targetsDisplay.get(cnt).getBorderColor());
			assertThat(targetsDisplay).extracting(FullCalendarDisplay::getTextColor)
					.contains(targetsDisplay.get(cnt).getTextColor());
		}
	}

	@Test
	void test_convertFullCalendarForm() {
		FullCalendarEntity target = ShiftManagementTestDataGenerator.getAnyEmployeeSchedule(EMPLOYEE.hoge, 1, 15);
		FullCalendarForm targetForm = FullCalendarHelper.convertFullCalendarForm(target);
		assertThat(targetForm.getId()).isEqualTo(target.getShiftId());
		assertThat(targetForm.getEmployeeId()).isEqualTo(target.getEmployee().getEmployeeId());
		assertThat(targetForm.getStart()).isEqualTo(target.getStart());
		assertThat(targetForm.getScheduledStart()).isEqualTo(target.getScheduledStart());
		assertThat(targetForm.getScheduledEnd()).isEqualTo(target.getScheduledEnd());
	}

	@Test
	void test_convertFullCalendarForm_invalid_because_target_is_null() {
		FullCalendarEntity target = null;
		assertThrows(NullPointerException.class, () -> FullCalendarHelper.convertFullCalendarForm(target));
	}

	@Test
	void test_convertFullCalendarForms() {
		List<FullCalendarEntity> targets = ShiftManagementTestDataGenerator.getAprilSchedules();
		List<FullCalendarDisplay> targetsDisplay = FullCalendarHelper.convertFullCalendarDisplays(targets);

		for (int cnt = 0; cnt < targetsDisplay.size(); cnt++) {
			assertThat(targetsDisplay).extracting(t -> t.getId()).contains(targets.get(cnt).getShiftId());
			assertThat(targetsDisplay).extracting(t -> t.getEmployeeId())
					.contains(targets.get(cnt).getEmployee().getEmployeeId());
			assertThat(targetsDisplay).extracting(t -> t.getTitle()).contains(targets.get(cnt).getEmployee().getName());
			assertThat(targetsDisplay).extracting(t -> t.getStart())
					.contains(targets.get(cnt).getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			assertThat(targetsDisplay).extracting(t -> t.getScheduledStart())
					.contains(targets.get(cnt).getScheduledStart().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
			assertThat(targetsDisplay).extracting(t -> t.getScheduledEnd())
					.contains(targets.get(cnt).getScheduledEnd().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		}
	}

	//	@Test
	//	void test_convertFullCalendarForms_when_targets_is_empty() {
	//		List<FullCalendarEntity> targets = new ArrayList<FullCalendarEntity>();
	//		//false
	//		assertThrows(NullPointerException.class,()-> FullCalendarHelper.convertFullCalendarDisplays(targets));
	//	}
	//	
	@Test
	void test_convertFullCalendarForms_when_targets_is_null() {
		List<FullCalendarEntity> targets = null;
		//true
		assertThrows(NullPointerException.class, () -> FullCalendarHelper.convertFullCalendarDisplays(targets));
	}
}
