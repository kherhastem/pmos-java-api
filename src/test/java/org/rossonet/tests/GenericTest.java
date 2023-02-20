package org.rossonet.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.ws.rs.core.Request;

import org.jdom2.Element;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import net.rossonet.pmos.client3.PmosClient3;
import net.rossonet.pmos.client3.ProcessMakerClient3;
import net.rossonet.pmos.client3.ProcessMakerClient3Exception;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.AddCaseNoteRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.AddCaseNoteResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.AssignUserToDepartmentRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.AssignUserToGroupRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CancelCaseRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CancelCaseResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CaseListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CaseListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CaseListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.ClaimCaseRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.ClaimCaseResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CreateDepartmentRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CreateDepartmentResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CreateGroupRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CreateGroupResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CreateUserRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.CreateUserResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.DeleteCaseRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.DeleteCaseResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.DepartmentListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.DepartmentListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.DepartmentListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.ExecuteTriggerRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GetCaseInfoRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GetCaseInfoResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GetCaseNotesRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GetCaseNotesResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GetVariablesNamesRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GetVariablesNamesResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GetVariablesRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GetVariablesResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GroupListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GroupListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.GroupListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.InformationUserRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.InformationUserResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.InformationUserStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.InputDocumentListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.InputDocumentListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.InputDocumentListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.InputDocumentProcessListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.InputDocumentProcessListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.InputDocumentProcessListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.Login;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.LoginResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.NewCaseImpersonateRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.NewCaseImpersonateResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.NewCaseRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.NewCaseResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.OutputDocumentListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.OutputDocumentListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.PauseCaseRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.PauseCaseResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.PmResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.ProcessListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.ProcessListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.ProcessListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.ReassignCaseRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.RemoveDocumentRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.RemoveDocumentResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.RemoveUserFromGroupRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.RoleListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.RoleListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.RoleListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.RouteCaseRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.RouteCaseResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.SendMessageRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.SendVariablesRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.TaskCaseRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.TaskCaseResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.TaskListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.TaskListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.TaskListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.TriggerListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.TriggerListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.TriggerListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.UnassignedCaseListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.UnassignedCaseListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.UnassignedCaseListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.UnpauseCaseRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.UnpauseCaseResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.UpdateUserRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.UpdateUserResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.UserListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.UserListResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.UserListStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.VariableListNameStruct;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.VariableListStruct;
import net.rossonet.pmos.client3.rest.ProcessMakerRestClient.AccessScope;

public class GenericTest {
	private static final Logger logger = Logger.getLogger(GenericTest.class.getName());
	
	String myUserId = "78287834963b5b6b54fede5075399220"; //cinzia.ena
	String myCaseId = "33563077063ea65a38908e6018337372";
	String testUserId = "20252165763e2b5f19ce230007838768";
	
	@Test
	public void addCaseNote() throws Exception  {
		final PmosClient3 client = connect();
		AddCaseNoteRequest caseNote = new AddCaseNoteRequest();
		caseNote.setCaseUid(myCaseId);
		ProcessListRequest processRequest = new ProcessListRequest();
		ProcessListResponse processResponse = client.processList(processRequest);
		caseNote.setProcessUid(processResponse.getProcesses()[0].getGuid());
		caseNote.setTaskUid("1234567890");
		caseNote.setUserUid(myUserId);
		caseNote.setNote("prova");
		AddCaseNoteResponse response = client.addCaseNote(caseNote);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void assignUserToDepartment() throws Exception {
		final PmosClient3 client = connect();
		AssignUserToDepartmentRequest request = new AssignUserToDepartmentRequest();
		request.setDepartmentId("1234567890");
		request.setUserId(myUserId);
		PmResponse response = client.assignUserToDepartment(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void assignUserToGroup() throws Exception {
		final PmosClient3 client = connect();
		AssignUserToGroupRequest request = new AssignUserToGroupRequest();
		request.setGroupId("1234567890");
		request.setUserId(myUserId);
		PmResponse response = client.assignUserToGroup(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void cancelCase() throws Exception{
		final PmosClient3 client = connect();
		CancelCaseRequest request = new CancelCaseRequest();
		request.setCaseUid(myCaseId);
		request.setUserUid(myUserId);
		request.setDelIndex("1");
		CancelCaseResponse response = client.cancelCase(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void caseList() throws Exception {
		final PmosClient3 client = connect();
		CaseListRequest request = new CaseListRequest();
		CaseListResponse response = client.caseList(request);  
//client.caseList genera ERRORE org.apache.axis2.AxisFault: org.apache.axis2.databinding.ADBException: Unexpected subelement
		System.out.println("Result --> ");
		for (CaseListStruct elem : response.getCases())
			System.out.println("--> "  + elem.getName());
		client.disconnect();
	} 
	
	@Test
	public void checkClient() throws ProcessMakerClient3Exception   {
		final PmosClient3 client = connect();
		System.out.println("Result --> "  + client.toString());
		client.disconnect();
	}
	
	@Test
	public void claimCase() throws ProcessMakerClient3Exception, RemoteException    {
		final PmosClient3 client = connect();
		ClaimCaseRequest request = new ClaimCaseRequest();
		request.setGuid(myCaseId);
		request.setDelIndex("1");
		ClaimCaseResponse response = client.claimCase(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void close() throws Exception {
		final PmosClient3 client = connect();
		client.close();
		System.out.println("Result --> "  + client.toString());
		client.disconnect();
	}
	
	@Test
	public void createDepartment() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		CreateDepartmentRequest request = new CreateDepartmentRequest();
		request.setName("Test Department");
		DepartmentListRequest  listRequest = new DepartmentListRequest();
		DepartmentListResponse listResponse = client.departmentList(listRequest);
		DepartmentListStruct firstDepartment = listResponse.getDepartments()[0];
		String parentUID = firstDepartment.getParentUID();
		request.setParentUID(parentUID);
		CreateDepartmentResponse response = client.createDepartment(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void createGroup() throws ProcessMakerClient3Exception, RemoteException {
		final PmosClient3 client = connect();
		CreateGroupRequest  request = new CreateGroupRequest();
		request.setName("gruppo di prova");
		CreateGroupResponse response = client.createGroup(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void createUser() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		CreateUserRequest request = new CreateUserRequest();
		request.setUserId("1234567890");
// setUserId il valore viene messo come username!
		request.setFirstname("Lyon");
		request.setLastname("Lloyd");
		request.setEmail("lloydl@xyz.com");
		request.setRole("PROCESSMAKER_OPERATOR");
		request.setPassword("12345");
		CreateUserResponse response = client.createUser(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void deleteCase() throws ProcessMakerClient3Exception, RemoteException  {
		final PmosClient3 client = connect();
		DeleteCaseRequest  request = new DeleteCaseRequest();
		request.setCaseUid(myCaseId);
		DeleteCaseResponse response = client.deleteCase(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void departmentList() throws ProcessMakerClient3Exception, RemoteException {
		final PmosClient3 client = connect();
		DepartmentListRequest  request = new DepartmentListRequest();
		DepartmentListResponse response = client.departmentList(request);	
		System.out.println("Result --> ");
		for(DepartmentListStruct elem : response.getDepartments())
			System.out.println("--> " + elem.getName());
		client.disconnect();
	}
	
	@Test
	public void executeTrigger() throws ProcessMakerClient3Exception, RemoteException {
		final PmosClient3 client = connect();
		ExecuteTriggerRequest  request = new ExecuteTriggerRequest();
		request.setTriggerIndex("0");
//client.caseList genera ERRORE org.apache.axis2.AxisFault: org.apache.axis2.databinding.ADBException: Unexpected subelement
		//CaseListStruct currentCase = client.caseList(new CaseListRequest()).getCases()[0];
		//request.setCaseId(currentCase.getGuid());
		request.setCaseId(myCaseId);
		PmResponse response = client.executeTrigger(request);	
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void getCaseInfo() throws ProcessMakerClient3Exception, RemoteException {
		final PmosClient3 client = connect();
		GetCaseInfoRequest  request = new GetCaseInfoRequest();
//client.caseList genera ERRORE org.apache.axis2.AxisFault: org.apache.axis2.databinding.ADBException: Unexpected subelement
		//CaseListStruct currentCase = client.caseList(new CaseListRequest()).getCases()[0];
		//request.setCaseId(currentCase.getGuid());
		request.setCaseId(myCaseId);
		GetCaseInfoResponse response = client.getCaseInfo(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
		
	@Test
	public void getCaseNotes() throws ProcessMakerClient3Exception, RemoteException {
		final PmosClient3 client = connect();
		GetCaseNotesRequest  request = new GetCaseNotesRequest();
		request.setApplicationID(System.getenv("PMOS_APP_ID"));
		request.setUserUid(myUserId);
// client.getCaseNotes genera ERRORE org.apache.axis2.AxisFault: org.apache.axis2.databinding.ADBException: Unexpected subelement	
		GetCaseNotesResponse response = client.getCaseNotes(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void getDynaform() throws ProcessMakerClient3Exception {
		final PmosClient3 client = connect();
		client.connectRestApi(System.getenv("PMOS_APP_ID"), System.getenv("PMOS_APP_SECRET"), AccessScope.ALL);
		String projectUid = "48035510563d949279032e9063832919"; //"condominio"
		String dynaformUid = client.getDynaforms(projectUid).getJSONObject(0).getString("dyn_uid");
		JSONObject response = client.getDynaform(projectUid, dynaformUid);	
		System.out.println("Result --> " + response.toString());
		client.disconnect();
	}
	
	@Test
	public void getProcessAsXml() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		client.connectRestApi(System.getenv("PMOS_APP_ID"), System.getenv("PMOS_APP_SECRET"), AccessScope.ALL);
		final ProcessListRequest processListRequest = new ProcessListRequest();
		final ProcessListStruct[] processList = client.processList(processListRequest).getProcesses();
		System.out.println("Result --> ");
		for (final ProcessListStruct p : processList) {
			System.out.println("---------------------------------");
			System.out.println(p.getName().toUpperCase());
			System.out.println(client.getProcessAsXml(p.getGuid()));

		}
		client.disconnect();
	}
	
	@Test
	public void getProcessAsXmlDocument() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		client.connectRestApi(System.getenv("PMOS_APP_ID"), System.getenv("PMOS_APP_SECRET"), AccessScope.ALL);
		final ProcessListRequest processListRequest = new ProcessListRequest();
		final ProcessListStruct[] processList = client.processList(processListRequest).getProcesses();
		System.out.println("Result --> ");
		for (final ProcessListStruct p : processList) {
			for (final Element c : client.getProcessAsXmlDocument(p.getGuid()).getRootElement().getChildren()) {
				System.out.println("---------------------------------");
				System.out.println(p.getName().toUpperCase());
				System.out.println(c.getName() + " -> " + c.getValue());
			}
		}
		client.disconnect();
	}
	
	@Test
	public void getServerBaseUrl() throws ProcessMakerClient3Exception{
		final PmosClient3 client = connect();
		System.out.println("Result --> " + client.getServerBaseUrl());
		client.disconnect();
	}
	
	@Test
	public void getSoapEndpoint() throws ProcessMakerClient3Exception{
		final PmosClient3 client = connect();
		System.out.println("Result --> " + ((ProcessMakerClient3)client).getSoapEndpoint());
		client.disconnect();
	}
	
	@Test
	public void getUsername() throws ProcessMakerClient3Exception{
		final PmosClient3 client = connect();
		System.out.println("Result --> " + client.getUsername());
		client.disconnect();
	}
	
	@Test
	public void getVariables() throws ProcessMakerClient3Exception, RemoteException {
		final PmosClient3 client = connect();
		GetVariablesRequest  request = new GetVariablesRequest();
		request.setCaseId(myCaseId);
// client.getVariables causa ERRORE Unable to sendViaPost to url[https://processi.bottegaio.net/sysdemo/en/neoclassic/services/soap2]
	//org.apache.axis2.AxisFault: variables cannot be null!!		
		GetVariablesResponse response = client.getVariables(request);	
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void getVariablesNames() throws ProcessMakerClient3Exception, RemoteException {
		final PmosClient3 client = connect();
		GetVariablesNamesRequest  request = new GetVariablesNamesRequest();
		request.setCaseId(myCaseId);
		GetVariablesNamesResponse response = client.getVariablesNames(request);	
		System.out.println("Result --> " + client.getVariablesNames(request) + ":");
		for (VariableListNameStruct elem : response.getVariables()) 
			System.out.println("--> " + elem.getName());
		client.disconnect();
	}
	
	@Test
	public void getWorkspace() throws ProcessMakerClient3Exception{
		final PmosClient3 client = connect();
		System.out.println("Result --> " + client.getWorkspace());
		client.disconnect();
	}
	
	@Test
	public void groupList() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		GroupListRequest request = new GroupListRequest();
		GroupListResponse response = client.groupList(request);
		System.out.println("Result --> ");
		for (GroupListStruct elem : response.getGroups())
			System.out.println("--> " + elem.getName());
		client.disconnect();
	}
	
	@Test
	public void informationUser() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		InformationUserRequest request = new InformationUserRequest();
		request.setUserUid(myUserId);
		InformationUserResponse response = client.informationUser(request);
		System.out.println("Result --> " + response.getMessage());
		for (InformationUserStruct elem : response.getInfo()) {
			System.out.println("--> " + elem.getFirstname() +" " + elem.getLastname() +" --> " + elem.getUsername() );
		}
		client.disconnect();
	}

	@Test
	public void inputDocumentList() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		InputDocumentListRequest request = new InputDocumentListRequest();
		request.setCaseId(myCaseId);
		InputDocumentListResponse response = client.inputDocumentList(request);
//client.inputDocumentList causa ERRORE org.apache.axis2.AxisFault: org.apache.axis2.databinding.ADBException: Unexpected subelement {http://www.processmaker.com}inputDocumentListResponse		
		System.out.println("Result --> ");
		for (InputDocumentListStruct elem : response.getDocuments()) {
				System.out.println("--> " + elem.getFilename());
		}
		client.disconnect();
	}
	
	@Test
	public void inputDocumentProcessList() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		InputDocumentProcessListRequest request = new InputDocumentProcessListRequest();
		request.setProcessId("1234567890");
		InputDocumentProcessListResponse response = client.inputDocumentProcessList(request);
		System.out.println("Result --> ");
		for (InputDocumentProcessListStruct elem : response.getDocuments()) {
				System.out.println("--> " + elem.getName());
		}
		client.disconnect();
	}
	
	@Test
	public void newCase() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		NewCaseRequest request = new NewCaseRequest();
		request.setProcessId("1234567890");
		request.setTaskId("1234567890");
		request.setVariables(null);  
//ERRORE variables cannot be null!! (COME devono essere strutturate le variabili?)
		NewCaseResponse response = client.newCase(request);
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void newCaseImpersonate() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		NewCaseImpersonateRequest request = new NewCaseImpersonateRequest();
		request.setProcessId("1234567890");
		request.setTaskId("1234567890");
		request.setUserId(myUserId);
		request.setVariables(null);  
//ERRORE variables cannot be null!! (COME devono essere strutturate le variabili?)
		NewCaseImpersonateResponse response = client.newCaseImpersonate(request);
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void outputDocumentList() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		OutputDocumentListRequest request = new OutputDocumentListRequest();
		 request.setCaseId(myCaseId);
		OutputDocumentListResponse response = client.outputDocumentList(request);
//client.outputDocumentList genera ERRORE org.apache.axis2.AxisFault: org.apache.axis2.databinding.ADBException: Unexpected subelement {http://www.processmaker.com}outputDocumentListResponse		
		System.out.println("Result --> ");
		for(Object elem: response.getDocuments())
			 System.out.println("--> " + elem.toString());
		client.disconnect();
	}
	
	@Test
	public void pauseCase() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		PauseCaseRequest request = new PauseCaseRequest();
		request.setCaseUid(myCaseId);
		request.setDelIndex("1");
		request.setUserUid(myUserId);
		PauseCaseResponse response = client.pauseCase(request);
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void processList() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		ProcessListRequest request = new ProcessListRequest();
		ProcessListResponse response = client.processList(request);
		System.out.println("Result --> ");
		for (ProcessListStruct elem : response.getProcesses())
			System.out.println("--> " + elem.getName());
		client.disconnect();
	}
	
	@Test
	public void reassignCase() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		ReassignCaseRequest request = new ReassignCaseRequest();
		request.setCaseId(myCaseId);
		request.setDelIndex("0");
		request.setUserIdSource(myUserId);
		request.setUserIdTarget("1234567890");
		PmResponse response = client.reassignCase(request);
// client.reassignCase genera ERRORE Org.apache.axis2.AxisFault: Uncaught TypeError: Argument 2 passed 
		//to Illuminate\Routing\UrlGenerator::__construct() must be an instance of 
		//Illuminate\Http\Request, null given, called 
		//in /opt/processmaker/vendor/laravel/framework/src/Illuminate/Routing/RoutingServiceProvider.php 
		//on line 62 and defined in /opt/processmaker/vendor/laravel/framework/src/Illuminate/Routing/UrlGenerator.php:120		
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void removeDocument() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		RemoveDocumentRequest request = new RemoveDocumentRequest();
		request.setAppDocUid("15157666063d94927c61420007779953");
		RemoveDocumentResponse response = client.removeDocument(request);
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void removeUserFromGroup() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		GroupListRequest groupRequest = new GroupListRequest();
		GroupListResponse groupResponse = client.groupList(groupRequest);
		GroupListStruct myGroup = null;
		for(GroupListStruct elem : groupResponse.getGroups()) {
			if (elem.getName().equals("Elettricisti")) 
				myGroup = elem;
		}
		if (myGroup != null) {
			RemoveUserFromGroupRequest request = new RemoveUserFromGroupRequest();
			request.setGroupId(myGroup.getGuid());
			request.setUserId(myUserId);
			PmResponse response = client.removeUserFromGroup(request);
			System.out.println("Result --> " + response.getMessage());
		} else {
			System.out.println("This group doesn't exist");
		}
		client.disconnect();
	}
	
	@Test
	public void roleList() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		RoleListRequest request = new RoleListRequest();
		RoleListResponse response = client.roleList(request);
		System.out.println("Result -->");
		for (RoleListStruct elem : response.getRoles())
			System.out.println("--> " + elem.getName());
		client.disconnect();
	}
	
	@Test
	public void routeCase() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		RouteCaseRequest request = new RouteCaseRequest();
		request.setCaseId(myCaseId);
		request.setDelIndex("0");
		RouteCaseResponse response = client.routeCase(request);
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void sendMessage() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		SendMessageRequest request = new SendMessageRequest();
		request.setCaseId(myCaseId);
		request.setFrom("cinzia.ena@gmail.com");
		request.setTo("lloydl@xyz.com");
		request.setSubject("Prova");
		request.setTemplate("Ciao");
		request.setCc("cinzia.ena@gmail.com");
		request.setBcc("cinzia.ena@gmail.com");
		PmResponse response = client.sendMessage(request);
// Result --> The Application row '33563077063ea65a38908e6018337372' doesn't exist!   //?????		
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void sendVariables() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		SendVariablesRequest request = new SendVariablesRequest();
		request.setCaseId(myCaseId);
		request.setVariables(null);
		PmResponse response = client.sendVariables(request);
//ERRORE variables cannot be null!! (COME devono essere strutturate le variabili?)		
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void taskCase() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		TaskCaseRequest request = new TaskCaseRequest();
		request.setCaseId(myCaseId);
		TaskCaseResponse response = client.taskCase(request);
// client.taskCase genera ERRORE org.apache.axis2.AxisFault: org.apache.axis2.databinding.ADBException: Unexpected subelement
		//{http://www.processmaker.com}taskCaseResponse	
		System.out.println("Result --> " + response.toString());
		client.disconnect();
	}
	
	@Test
	public void taskList() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		TaskListRequest request = new TaskListRequest();
		TaskListResponse response = client.taskList(request);
// client.taskList genera ERRORE org.apache.axis2.AxisFault: org.apache.axis2.databinding.ADBException: Unexpected subelement
		//{http://www.processmaker.com}taskListResponse		
		System.out.println("Result --> ");
		for (TaskListStruct elem : response.getTasks())
			System.out.println("--> " + elem.getName());
		client.disconnect();
	}
	
	@Test
	public void triggerList() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		TriggerListRequest request = new TriggerListRequest();
		TriggerListResponse response = client.triggerList(request);	
		System.out.println("Result --> ");
		for (TriggerListStruct elem : response.getTriggers())
			System.out.println("--> " + elem.getName());
		client.disconnect();
	}
	
	@Test
	public void unassignedCaseList() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		UnassignedCaseListRequest request = new UnassignedCaseListRequest();
		UnassignedCaseListResponse response = client.unassignedCaseList(request);	
// client.unassignedCaseList genera	ERRORE:	org.apache.axis2.AxisFault: org.apache.axis2.databinding.ADBException: Unexpected subelement 
		//{http://www.processmaker.com}unassignedCaseListResponse
		System.out.println("Result --> ");
		for (UnassignedCaseListStruct elem : response.getCases())
			System.out.println("--> " + elem.getName());
		client.disconnect();
	}
	
	@Test
	public void unpauseCase() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		UnpauseCaseRequest request = new UnpauseCaseRequest();
		request.setCaseUid(myCaseId);
		request.setDelIndex("1");
		request.setUserUid(myUserId);
		UnpauseCaseResponse response = client.unpauseCase(request);	
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void updateUser() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		UpdateUserRequest request = new UpdateUserRequest();
		request.setUserUid(myUserId);
// PERCHÈ TUTTI I PARAMETRI DEVONO ESSERE ARRAY DI STRINGHE??? 
	//E PERCHÈ NON SI PUÒ AGGIORNARE UN SOLO PARAMETRO?
		String[] emails = {"cinzia.ena@gmail.com"};
		request.setEmail(emails);
		UpdateUserResponse response = client.updateUser(request);	
		System.out.println("Result --> " + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void userList() throws ProcessMakerClient3Exception, RemoteException{
		final PmosClient3 client = connect();
		UserListRequest request = new UserListRequest();
		UserListResponse response = client.userList(request);	
		System.out.println("Result --> ");
		for (UserListStruct elem : response.getUsers())
			System.out.println("--> userName: " + elem.getName() + ", id: " + elem.getGuid());
		client.disconnect();
	}
	//NOT TEST/////////////////////////////////////////////////////////////
	
	@AfterEach
	public void cleanTestsContext() throws Exception {
		logger.info("test completed");
	}
	
	private PmosClient3 connect() throws ProcessMakerClient3Exception {
		final String endpoint = System.getenv("PMOS_ENDPOINT");
		final String username = System.getenv("PMOS_USERNAME");
		System.out.println("try username " + username + " at " + endpoint);
		final PmosClient3 client = PmosClient3.getNewClient(endpoint, "demo", username, System.getenv("PMOS_PASSWORD"));
		client.connect();
		assertNotNull(client.getSessionId());
		System.out.println("sessionId => " + client.getSessionId());
		return client;
	}
	
	public UserListStruct getUserByUserName(PmosClient3 client, String userName) throws ProcessMakerClient3Exception, RemoteException {
		UserListRequest request = new UserListRequest();
		UserListResponse response = client.userList(request);	
		UserListStruct user = null;
		for (UserListStruct elem : response.getUsers())
			if (elem.getName().equals(userName))
				user = elem;
		return user;
	}
	
	public UserListStruct getUserById(PmosClient3 client, String userId) throws ProcessMakerClient3Exception, RemoteException {
		UserListRequest request = new UserListRequest();
		UserListResponse response = client.userList(request);	
		UserListStruct user = null;
		for (UserListStruct elem : response.getUsers())
			if (elem.getName().equals(userId))
				user = elem;
		return user;
	}
	
}
