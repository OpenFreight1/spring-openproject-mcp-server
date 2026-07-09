package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.tklein.tklab.openproject.mcp.dto.GroupDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GroupToolsTest {

  // mocked
  private final OpenProjectApiClient openProjectApiClient = mock(OpenProjectApiClient.class);

  @InjectMocks
  private GroupTools groupTools;

  @Test
  void groupList_delegatesToClient_andReturnsResult() {
    // GIVEN
    List<GroupDto> expected = List.of(
        new GroupDto(1, "Developers", "/api/v3/groups/1"),
        new GroupDto(2, "Testers", "/api/v3/groups/2")
    );
    when(openProjectApiClient.groupList()).thenReturn(expected);

    // WHEN
    List<GroupDto> result = groupTools.groupList();

    // THEN
    assertSame(expected, result);
    verify(openProjectApiClient).groupList();
  }
}
