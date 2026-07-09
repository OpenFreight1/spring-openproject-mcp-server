package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.tklein.tklab.openproject.mcp.dto.UserDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserToolsTest {

  // mocked
  private final OpenProjectApiClient openProjectApiClient = mock(OpenProjectApiClient.class);

  @InjectMocks
  private UserTools userTools;

  @Test
  void userList_delegatesToClient_andReturnsResult() {
    // GIVEN
    List<UserDto> expected = List.of(
        new UserDto(1, "/api/v3/users/1", "Alice"),
        new UserDto(2, "/api/v3/users/2", "Bob")
    );
    when(openProjectApiClient.userList("ali")).thenReturn(expected);

    // WHEN
    List<UserDto> result = userTools.userList("ali");

    // THEN
    assertSame(expected, result);
    verify(openProjectApiClient).userList("ali");
  }
}
