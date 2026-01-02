package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.tklein.tklab.openproject.mcp.dto.PriorityDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriorityToolsTest {

  // mocked
  private final OpenProjectApiClient openProjectApiClient = mock(OpenProjectApiClient.class);

  @InjectMocks
  private PriorityTools priorityTools;

  @Test
  void priorityList_delegatesToClient_andReturnsResult() {
    // GIVEN
    List<PriorityDto> expected = List.of(
        new PriorityDto(1, "Low", 1, "/api/v3/priorities/1"),
        new PriorityDto(2, "Normal", 2, "/api/v3/priorities/2")
    );
    when(openProjectApiClient.priorityList()).thenReturn(expected);

    // WHEN
    List<PriorityDto> result = priorityTools.priorityList();

    // THEN
    assertSame(expected, result);
    verify(openProjectApiClient).priorityList();
  }
}