package com.wanghao.flowabletest;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author wanghao
 * @Description
 * @date 2018-02-22 9:10
 */
public class HolidayRequest {
    public static void main(String[] args) {
        ProcessEngine processEngine = getProcessEngine("mysql");

        Deployment deployment = deploy(processEngine);
        System.out.println(deployment.getId());
        System.out.println(deployment.getName());
        System.out.println(deployment.getCategory());
        System.out.println(deployment.getEngineVersion());
        System.out.println(deployment.getKey());
        System.out.println(deployment.getTenantId());
        printDefinition(processEngine, deployment);
        startCLIProcess(processEngine);
    }

    // 启动流程引擎
    public static ProcessEngine getProcessEngine(String type) {
        ProcessEngineConfiguration cfg = null;

        if ("mysql".equals(type))
            cfg = new StandaloneProcessEngineConfiguration()
                    .setJdbcUrl("jdbc:mysql://localhost:3306/flowable?characterEncoding=UTF-8")
                    .setJdbcUsername("root")
                    .setJdbcPassword("123456")
                    .setJdbcDriver("com.mysql.jdbc.Driver")
                    .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        if ("h2".equals(type))
            cfg = new StandaloneProcessEngineConfiguration()
                    .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                    .setJdbcUsername("sa")
                    .setJdbcPassword("")
                    .setJdbcDriver("org.h2.Driver")
                    .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        return cfg.buildProcessEngine();
    }

    // 部署流程到引擎中
    private static Deployment deploy(ProcessEngine processEngine) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .name("请假流程")
                .category("testCategory")
                .key("testKey")
                .tenantId("testTenantId")
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();
        return deployment;
    }

    // 打印流程名称
    private static void printDefinition(ProcessEngine processEngine, Deployment deployment) {
        ProcessDefinition processDefinition = processEngine.getRepositoryService()
                // true：级联删除，删除和当前规则相关的所有信息
                // .deleteDeployment(deployment.getId(), true)
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                // .processDefinitionId(processDefinitionId)//使用流程定义ID查询
                // .processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
                // .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
        System.out.println(processDefinition.getId());// {key}:{version}:{随机值}
        System.out.println(processDefinition.getTenantId());
        System.out.println(processDefinition.getKey());
        System.out.println(processDefinition.getEngineVersion());
        System.out.println(processDefinition.getVersion());
        System.out.println(processDefinition.getCategory());
        System.out.println(processDefinition.getDeploymentId());
        System.out.println(processDefinition.getDescription());
    }

    // 通过命令行参数启动一个流程
    private static void startCLIProcess(ProcessEngine processEngine) {
        // 申请请假
        Scanner scanner = new Scanner(System.in);

        System.out.println("Who are you?");
        String employee = scanner.nextLine();

        System.out.println("How many holidays do you want to request?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        System.out.println("Why do you need them?");
        String description = scanner.nextLine();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String, Object> variables = new HashMap<String, Object>(16);
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);
        ProcessInstance processInstance =
                runtimeService.startProcessInstanceByKey("holidayRequest", variables);
        System.out.println("=================ProcessInstance:=================");
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getBusinessKey());
        System.out.println(processInstance.getCallbackId());
        System.out.println(processInstance.getStartUserId());
        System.out.println("=================ProcessInstance:=================");

        // 经理查看请假列表
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }

        // 同意或拒绝申请
        System.out.println("Which task would you like to complete?");
        int taskIndex = Integer.valueOf(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + " wants " +
                processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");

        boolean approved = scanner.nextLine().toLowerCase().equals("y");
        variables = new HashMap<String, Object>();
        variables.put("approved", approved);
        taskService.complete(task.getId(), variables);
    }
}
