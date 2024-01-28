package com.tritonkor.grouptester.appui;

import static com.tritonkor.grouptester.appui.TestView.TesterMenu.CREATE_GROUP;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.EXIT;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.VIEW_RESULTS;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.VIEW_REPORTS;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.JOIN_THE_GROUP;

import com.tritonkor.grouptester.domain.contract.GroupService;
import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.dto.GroupAddDto;
import com.tritonkor.grouptester.domain.dto.UserAddDto;
import com.tritonkor.grouptester.domain.exception.AuthException;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.entity.impl.User.Role;
import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

public class TestView implements Renderable {

    private final TestService testService;
    private final ResultService resultService;
    private final GroupService groupService;

    private User currentUser;

    public TestView(TestService testService, ResultService resultService, GroupService groupService) {
        this.testService = testService;
        this.resultService = resultService;
        this.groupService = groupService;
    }

    @Override
    public void render() throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        promptBuilder.createListPrompt()
                .name("tester-menu")
                .message("Tester Menu  " + TesterMenu.USERNAME.getName() + currentUser.getUsername()
                        + " " + TesterMenu.ROLE.getName() + currentUser.getRole())
                .newItem(JOIN_THE_GROUP.toString()).text(JOIN_THE_GROUP.getName()).add()
                .newItem(CREATE_GROUP.toString()).text(CREATE_GROUP.getName()).add()
                .newItem(VIEW_RESULTS.toString()).text(VIEW_RESULTS.getName()).add()
                .newItem(VIEW_REPORTS.toString()).text(VIEW_REPORTS.getName()).add()
                .newItem(EXIT.toString()).text(EXIT.getName()).add()
                .addPrompt();

        var result = prompt.prompt(promptBuilder.build());
        ListResult resultItem = (ListResult) result.get("tester-menu");

        TesterMenu selectedItem = TesterMenu.valueOf(resultItem.getSelectedId());
        process(selectedItem);
    }

    private void process(TesterMenu selectedItem) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        switch (selectedItem) {

            case JOIN_THE_GROUP -> {
                System.out.println("List of groups:\n");

                ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt().name("groups-list");

                Set<Group> groups = groupService.getAll();
                for(Group group : groups){
                    listPromptBuilder.newItem(group.getName()).text(group.getName()).add();
                }
                listPromptBuilder.addPrompt();

                var result = prompt.prompt(listPromptBuilder.addPrompt().build());
                ListResult groupInput = (ListResult) result.get("groups-list");

                Group group = groupService.findByName(groupInput.getSelectedId());

                group.addObserver(currentUser);
                System.out.println(group.getUsers());
            }
            case CREATE_GROUP -> {
                if (currentUser.getRole() == Role.GENERAL) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();

                    System.out.println("Access to create groups is denied");
                    this.render();
                }

                boolean dataCorrect = true;

                do {
                    promptBuilder.createInputPrompt()
                            .name("name")
                            .message("Type group name: ")
                            .addPrompt();

                    try {

                        var result = prompt.prompt(promptBuilder.build());

                        var groupNameInput = (InputResult) result.get("name");
                        LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

                        GroupAddDto groupAddDto = new GroupAddDto(UUID.randomUUID(), groupNameInput.getInput(), null, createdAt);
                        try {

                            groupService.add(groupAddDto);
                            dataCorrect = true;
                        } catch (AuthException e) {
                            System.err.println(e.getMessage());
                        }
                    } catch (EntityArgumentException e) {
                        prompt = new ConsolePrompt();
                        promptBuilder = prompt.getPromptBuilder();
                        ConsolClearer.clearConsole();
                        System.out.println(e.getMessage());

                        dataCorrect = false;
                    }
                } while (!dataCorrect);
            }
            case VIEW_RESULTS -> {
            }
            case VIEW_REPORTS -> {
            }
            case EXIT -> {
            }
        }
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    enum TesterMenu {
        JOIN_THE_GROUP("Join the group"),
        CREATE_GROUP("Create new group"),
        VIEW_RESULTS("View your results"),
        VIEW_REPORTS("View your reports"),
        USERNAME("Username: "),
        ROLE("Role: "),
        EXIT("Exit");

        private final String name;

        TesterMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
