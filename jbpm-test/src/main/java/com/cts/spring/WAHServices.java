package com.cts.spring;

import java.util.Map;


import javax.persistence.EntityManagerFactory;

 

import org.jbpm.process.workitem.bpmn2.ServiceTaskHandler;

import org.jbpm.services.api.ProcessService;

import org.kie.api.runtime.KieSession;

import org.kie.api.runtime.manager.RuntimeEngine;

import org.kie.api.runtime.manager.RuntimeEnvironment;

import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;

import org.kie.api.runtime.manager.RuntimeManager;

import org.kie.api.runtime.manager.RuntimeManagerFactory;

import org.kie.api.runtime.process.ProcessInstance;

import org.kie.api.runtime.process.WorkItemManager;

import org.kie.api.task.TaskService;

import org.kie.internal.runtime.manager.context.EmptyContext;

import org.kie.internal.task.api.UserGroupCallback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

 

@Service
public class WAHServices {

    private KieSession kieSession;

          @Autowired
          private TaskService taskService;
          private RuntimeManager runtimeManager;
          private RuntimeEnvironment runtimeEnvironment;

          @Autowired
          private ProcessService processService;
                
          @Autowired
          private UserGroupCallback userGroupCallback;
                
          @Autowired
          @Qualifier("jbpmEntityManagerFactory")
          private EntityManagerFactory entityManagerFactory;
                
          @Autowired
          public WAHServices(ProcessService processService, EntityManagerFactory entityManagerFactory,
						UserGroupCallback userGroupCallback) {
					super();
					this.processService = processService;
					this.entityManagerFactory = entityManagerFactory;
					this.userGroupCallback = userGroupCallback;
				}
				
                public KieSession getKieSession() {
                	return kieSession;
                }

                public void setKieSession(KieSession kieSession) {
                	this.kieSession = kieSession;
                }

                public TaskService getTaskService() {
                return taskService;
                }

                public void setTaskService(TaskService taskService) {
                	this.taskService = taskService;
                }

                public RuntimeManager getRuntimeManager() {
                                return runtimeManager;
                                
                }

                public void setRuntimeManager(RuntimeManager runtimeManager) {
                                this.runtimeManager = runtimeManager;
                }
                
                public RuntimeEnvironment getRuntimeEnvironment() {
                return runtimeEnvironment;
                }

                public void setRuntimeEnvironment(RuntimeEnvironment runtimeEnvironment) {
                this.runtimeEnvironment = runtimeEnvironment;

                }

                public ProcessService getProcessService() {
                return processService;
                }

 

                public void setProcessService(ProcessService processService) {

                                this.processService = processService;

                }

 

                public EntityManagerFactory getEntityManagerFactory() {

                                return entityManagerFactory;

                }

 

                public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {

                                this.entityManagerFactory = entityManagerFactory;

                }

 

                public UserGroupCallback getUserGroupCallback() {
                          return userGroupCallback;

                }

 

                public void setUserGroupCallback(UserGroupCallback userGroupCallback) {

                                this.userGroupCallback = userGroupCallback;

                }

 

                private RuntimeManager buidRuntimeManager() {
                                RuntimeEnvironment env = RuntimeEnvironmentBuilder.Factory.get().newClasspathKmoduleDefaultBuilder()
                                                                .entityManagerFactory(this.entityManagerFactory).userGroupCallback(this.userGroupCallback)
                                                                .persistence(true).get();
                                System.out.println("RuntimeEnvironment :: "+env);
                                RuntimeManager rm = null;
                                try {
                                rm = RuntimeManagerFactory.Factory.get().newSingletonRuntimeManager(env);
                                }catch (Exception e) {
                                                e.printStackTrace();
                                                System.out.println("Exception occur at creating RuntimeManager :: "+e.getMessage());
                                }
                                return rm;

                }

                private RuntimeEngine getRuntimeEngine() {
                         return this.runtimeManager.getRuntimeEngine(EmptyContext.get());
                }

                private KieSession getkiesession() {
                                if (this.runtimeManager == null) {
                                                this.runtimeManager = this.buidRuntimeManager();
                                                this.kieSession = this.getRuntimeEngine().getKieSession();
                                                WorkItemManager wim = this.kieSession.getWorkItemManager();
                                                wim.registerWorkItemHandler("Service Task", new ServiceTaskHandler());
                                }
                                return this.kieSession;
                }

 

                public Long startProcess(String processId, Map<String, Object> params) {
                                this.kieSession = this.getkiesession();
                                ProcessInstance pi = this.kieSession.startProcess(processId, params);
                                return pi.getId();
                }

 

}

