package com.example.webapp.utility;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.example.webapp.utility.ShiftRequestDeadLine;

class ShiftRequestDeadLineTest {
	
	@Test
	void test_toString() {
		var jan=new ShiftRequestDeadLine(LocalDate.of(2025, 1, 1));
		var feb=new ShiftRequestDeadLine(LocalDate.of(2025, 2, 1));
		var mar=new ShiftRequestDeadLine(LocalDate.of(2025, 3, 1));
		var apr=new ShiftRequestDeadLine(LocalDate.of(2025, 4, 1));
		var may=new ShiftRequestDeadLine(LocalDate.of(2025, 5, 1));
		var jun=new ShiftRequestDeadLine(LocalDate.of(2025, 6, 1));
		var jur=new ShiftRequestDeadLine(LocalDate.of(2025, 7, 1));
		var aug=new ShiftRequestDeadLine(LocalDate.of(2025, 8, 1));
		var sep=new ShiftRequestDeadLine(LocalDate.of(2025, 9, 1));
		var oct=new ShiftRequestDeadLine(LocalDate.of(2025, 10, 1));
		var nov=new ShiftRequestDeadLine(LocalDate.of(2025, 11, 1));
		var des=new ShiftRequestDeadLine(LocalDate.of(2025, 12, 1));
		
		assertThat(jan.toString()).isEqualTo("1月17日（金）");
		assertThat(feb.toString()).isEqualTo("2月21日（金）");
		assertThat(mar.toString()).isEqualTo("3月21日（金）");
		assertThat(apr.toString()).isEqualTo("4月18日（金）");
		assertThat(may.toString()).isEqualTo("5月16日（金）");
		assertThat(jun.toString()).isEqualTo("6月20日（金）");
		assertThat(jur.toString()).isEqualTo("7月18日（金）");
		assertThat(aug.toString()).isEqualTo("8月15日（金）");
		assertThat(sep.toString()).isEqualTo("9月19日（金）");
		assertThat(oct.toString()).isEqualTo("10月17日（金）");
		assertThat(nov.toString()).isEqualTo("11月21日（金）");
		assertThat(des.toString()).isEqualTo("12月19日（金）");
	}

	@Test
	void test_isOverDeadLine() {
		var jan=new ShiftRequestDeadLine(LocalDate.of(2025, 1, 1));
		LocalDate before=LocalDate.of(2025, 1, 16);
		LocalDate just=LocalDate.of(2025, 1, 17);
		LocalDate after=LocalDate.of(2025, 1, 18);
		
		assertThat(jan.isOverDeadLine(before)).isFalse();
		assertThat(jan.isOverDeadLine(just)).isFalse();
		assertThat(jan.isOverDeadLine(after)).isTrue();
	}
	
	@Test
	void test_isJustOrUnderDeadLine() {
		var jan=new ShiftRequestDeadLine(LocalDate.of(2025, 1, 1));
		LocalDate before=LocalDate.of(2025, 1, 16);
		LocalDate just=LocalDate.of(2025, 1, 17);
		LocalDate after=LocalDate.of(2025, 1, 18);
		
		assertThat(jan.isJustOrUnderDeadLine(before)).isTrue();
		assertThat(jan.isJustOrUnderDeadLine(just)).isTrue();
		assertThat(jan.isJustOrUnderDeadLine(after)).isFalse();
	}

}
