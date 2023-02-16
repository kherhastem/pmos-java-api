package org.rossonet.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import org.jdom2.Element;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import net.rossonet.pmos.client3.PmosClient3;
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
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.PmResponse;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.ProcessListRequest;
import net.rossonet.pmos.client3.generated.ProcessMakerServiceStub.ProcessListStruct;
import net.rossonet.pmos.client3.rest.ProcessMakerRestClient.AccessScope;

public class GenericTest {
	private static final Logger logger = Logger.getLogger(GenericTest.class.getName());
	
	@Test
	public void addCaseNote() throws Exception  {
		final PmosClient3 client = connect();
		AddCaseNoteRequest caseNote = new AddCaseNoteRequest();
		caseNote.setCaseUid("1234567890");
		caseNote.setProcessUid("1234567890");
		caseNote.setTaskUid("1234567890");
		caseNote.setUserUid("1234567890");
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
		request.setUserId("1234567890");
		PmResponse response = client.assignUserToDepartment(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void assignUserToGroup() throws Exception {
		final PmosClient3 client = connect();
		AssignUserToGroupRequest request = new AssignUserToGroupRequest();
		request.setGroupId("1234567890");
		request.setUserId("1234567890");
		PmResponse response = client.assignUserToGroup(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
	
	@Test
	public void cancelCase() throws Exception{
		final PmosClient3 client = connect();
		CancelCaseRequest request = new CancelCaseRequest();
		request.setCaseUid("1234567890");
		request.setUserUid("1234567890");
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
		System.out.println("Result --> "  + response.toString());
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
		request.setGuid("1234567890");
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
		request.setCaseUid("33563077063ea65a38908e6018337372");
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
		request.setCaseId("30137984363ea64216c24a0050939668");
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
		request.setCaseId("30137984363ea64216c24a0050939668");
		GetCaseInfoResponse response = client.getCaseInfo(request);
		System.out.println("Result --> "  + response.getMessage());
		client.disconnect();
	}
		
	@Test
	public void getCaseNotes() throws ProcessMakerClient3Exception, RemoteException {
		final PmosClient3 client = connect();
		GetCaseNotesRequest  request = new GetCaseNotesRequest();
		request.setApplicationID(System.getenv("PMOS_APP_ID"));
		request.setUserUid(System.getenv("PMOS_USERNAME"));
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
}
