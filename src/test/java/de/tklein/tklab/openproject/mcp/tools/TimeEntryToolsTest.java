package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.tklein.tklab.openproject.mcp.dto.TimeEntryActivityDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimeEntryToolsTest {

  // mocked
  private final OpenProjectApiClient openProjectApiClient = mock(OpenProjectApiClient.class);

  @InjectMocks
  private TimeEntryTools timeEntryTools;

  @Test
  void timeEntryActivityList_delegatesToClient_andReturnsResult() {
    // GIVEN
    List<TimeEntryActivityDto> expected = List.of(
        new TimeEntryActivityDto(1, "Development", true, "/api/v3/time_entries/activities/1"),
        new TimeEntryActivityDto(2, "Management", false, "/api/v3/time_entries/activities/2")
    );
    when(openProjectApiClient.timeEntryActivityList()).thenReturn(expected);

    // WHEN
    List<TimeEntryActivityDto> result = timeEntryTools.timeEntryActivityList();

    // THEN
    assertSame(expected, result);
    verify(openProjectApiClient).timeEntryActivityList();
  }
}
