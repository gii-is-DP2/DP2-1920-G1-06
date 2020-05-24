
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class RoomServiceTests {

	@Autowired
	protected RoomService			roomService;

	private final static Integer	TEST_PROPERTY_ID	= 1;


	@Test
	void shouldFindARoomWithCorrectId() {
		Room room = this.roomService.findRoomById(RoomServiceTests.TEST_PROPERTY_ID);

		Assertions.assertThat(room.getRoomNumber()).isEqualTo("1");
	}

	@Test
	void shouldFindRoomsByPropertyId() {
		Collection<Room> room = this.roomService.findRoomByPropertyId(RoomServiceTests.TEST_PROPERTY_ID);

		Assertions.assertThat(room.size()).isEqualTo(4);

	}
}
