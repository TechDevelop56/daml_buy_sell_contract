// Copyright (c) 2021 Digital Asset (Switzerland) GmbH and/or its affiliates. All rights reserved.
// SPDX-License-Identifier: Apache-2.0

package com.daml.hunter;

import com.daml.ledger.rxjava.DamlLedgerClient;
import com.daml.ledger.rxjava.LedgerClient;
import com.daml.hunter.model.hunter.Agreement_order_details;
import com.daml.hunter.model.hunter.Deal_Initiate;
import com.daml.hunter.model.hunter.Initiate_Shipment;
import com.daml.hunter.model.hunter.Invoice;
import com.daml.hunter.model.hunter.Order;
import com.daml.hunter.model.hunter.Payment;
import com.daml.hunter.model.hunter.Shipment;
import com.daml.hunter.model.hunter.Shipment_details;
import com.daml.ledger.javaapi.data.*;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.protobuf.Empty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Spark;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import static spark.Spark.before;

public class HunterMain {
    private final static Logger logger = LoggerFactory.getLogger(HunterMain.class);
    // application id used for sending commands
    public static final String APP_ID = "IouApp";
	/*
	 * public static DamlLedgerClient client = null; public static TransactionFilter
	 * iouFilter = null;
	 */
    

    public static void main(String[] args) {
    		Gson g = new Gson();
            // create a client object to access services on the ledger
            DamlLedgerClient client = DamlLedgerClient.forHostWithLedgerIdDiscovery("localhost", 6865, Optional.empty());

            // Connects to the ledger and runs initial validationiou
            client.connect();

            String ledgerId = client.getLedgerId();

            logger.info("ledger-id: {}", ledgerId);
            
           

        Spark.port(8080);
        
        Spark.options("*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });
        
        before("*", (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Content-Type", "application/json");
		});
//*******************************************************************************************************************************************//
//													GET URL's starts from here ...!
//*******************************************************************************************************************************************//
        
      //  Spark.get("/getDeal_Initiate/:name", "application/json", (req, res) -> g.toJson(getDeal_Initiate(client,req.params("name"))));

        Spark.get("/getInvoice/:name", "application/json", (req, res) -> g.toJson(getInvoice(client,req.params("name"))));

        Spark.get("/getAggrementDetails/:name", "application/json", (req, res) -> g.toJson(getAggrementDetails(client,req.params("name"))));

        Spark.get("/getOrderDetails/:name", "application/json", (req, res) -> g.toJson(getOrderDetails(client,req.params("name"))));

     //   Spark.get("/getPaymentDetails/:name", "application/json", (req, res) -> g.toJson(getPaymentDetails(client,req.params("name"))));

        
        
//*******************************************************************************************************************************************//
//        											Post methods starts from here ...!
//*******************************************************************************************************************************************//
        
        Spark.put("/Deal_Initiate/:name", (req, res) -> {
        	System.out.println(req.body());
        	Deal_Initiate tm	 = g.fromJson(req.body(), Deal_Initiate.class);         	
            CreateCommand CreateInvestment = tm.create();
            submit(client, req.params("name"), CreateInvestment);
            return "Deal Has been submitted.";
        }, g::toJson);
        

        
        Spark.post("/acceptDeal_Initiate/:name", (req, res) -> {   	
        	System.out.println(req.body());
        	TransactionFilter iouFilter = filterFor(Deal_Initiate.TEMPLATE_ID, req.params("name"));       	
            
            BiMap<String, Deal_Initiate.ContractId> idMap = Maps.synchronizedBiMap(HashBiMap.create());
            AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

            client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                    .blockingForEach(response -> {
                        response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                        response.getCreatedEvents().stream()
                                .map(Deal_Initiate.Contract::fromCreatedEvent)
                                .forEach(contract -> {                                    
                                  //  idMap.put(contract.id.contractId, contract.id);
                                	  idMap.put("ID", contract.id);
                                });
                    });
        	Map m = g.fromJson(req.body(), Map.class);        	
        	Deal_Initiate.ContractId contractId = (Deal_Initiate.ContractId) idMap.get("ID");         	
        	ExerciseCommand iouCreate = contractId.exerciseAccept_Deal();
            submit(client, req.params("name"), iouCreate);
            String party = req.params("name");
            return "Deal Accepted by "+party+"..!";
        }, g::toJson);        
        
      
        
        Spark.post("/uploadDocuments/:name", (req, res) -> {   	
        	System.out.println(req.body());
        	TransactionFilter iouFilter = filterFor(Agreement_order_details.TEMPLATE_ID, req.params("name"));       	
            
            BiMap<String, Agreement_order_details.ContractId> idMap = Maps.synchronizedBiMap(HashBiMap.create());
            AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

            client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                    .blockingForEach(response -> {
                        response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                        response.getCreatedEvents().stream()
                                .map(Agreement_order_details.Contract::fromCreatedEvent)
                                .forEach(contract -> {                                    
                                  //  idMap.put(contract.id.contractId, contract.id);
                                	  idMap.put("ID", contract.id);
                                });
                    });
        	Map m = g.fromJson(req.body(), Map.class);        	
        	Agreement_order_details.ContractId contractId = (Agreement_order_details.ContractId) idMap.get("ID"); 
        	List<String> docs=(List<String>) m.get("document_name");
        	ExerciseCommand iouCreate = contractId.exerciseUpload_documents(String.valueOf(m.get("uploader")),docs);
            submit(client, req.params("name"), iouCreate);
            String party = req.params("name");
            return "Documents has been uploaded by "+ party;
        }, g::toJson);        
        
        Spark.post("/initiateShipment/:name", (req, res) -> {   	
        	System.out.println(req.body());
        	TransactionFilter iouFilter = filterFor(Agreement_order_details.TEMPLATE_ID, req.params("name"));       	
            
            BiMap<String, Agreement_order_details.ContractId> idMap = Maps.synchronizedBiMap(HashBiMap.create());
            AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

            client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                    .blockingForEach(response -> {
                        response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                        response.getCreatedEvents().stream()
                                .map(Agreement_order_details.Contract::fromCreatedEvent)
                                .forEach(contract -> {                                    
                                  //  idMap.put(contract.id.contractId, contract.id);
                                	  idMap.put("ID", contract.id);
                                });
                    });
        	Map m = g.fromJson(req.body(), Map.class);        	
        	Agreement_order_details.ContractId contractId = (Agreement_order_details.ContractId) idMap.get("ID"); 
        	//List<String> docs=(List<String>) m.get("document_name");
        	Shipment_details details=new Shipment_details(String.valueOf(m.get("deliveryLocation")),String.valueOf(m.get("delivery_starting_date")),String.valueOf(m.get("delivery_end_date"))); 
        	ExerciseCommand iouCreate = contractId.exerciseInitiate_Shipment(details);
            submit(client, req.params("name"), iouCreate);
            String party = req.params("name");
            return "Shipment has been initialised by "+party;
        }, g::toJson);      
        
        Spark.post("/acceptShipment/:name", (req, res) -> {   	
        	System.out.println(req.body());
        	TransactionFilter iouFilter = filterFor(Shipment.TEMPLATE_ID, req.params("name"));       	
            
            BiMap<String, Shipment.ContractId> idMap = Maps.synchronizedBiMap(HashBiMap.create());
            AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

            client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                    .blockingForEach(response -> {
                        response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                        response.getCreatedEvents().stream()
                                .map(Shipment.Contract::fromCreatedEvent)
                                .forEach(contract -> {                                    
                                  //  idMap.put(contract.id.contractId, contract.id);
                                	  idMap.put("ID", contract.id);
                                });
                    });
        	Map m = g.fromJson(req.body(), Map.class);        	
        	Shipment.ContractId contractId = (Shipment.ContractId) idMap.get("ID");         	
        	ExerciseCommand iouCreate = contractId.exerciseAccept_Shipment();
            submit(client, req.params("name"), iouCreate);
            String party = req.params("name");
            return "Shipment has been accepted by "+party;
        }, g::toJson);       
        
        Spark.post("/initiatePayment/:name", (req, res) -> {   	
        	System.out.println(req.body());
        	TransactionFilter iouFilter = filterFor(Order.TEMPLATE_ID, req.params("name"));       	
            
            BiMap<String, Order.ContractId> idMap = Maps.synchronizedBiMap(HashBiMap.create());
            AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

            client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                    .blockingForEach(response -> {
                        response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                        response.getCreatedEvents().stream()
                                .map(Order.Contract::fromCreatedEvent)
                                .forEach(contract -> {                                    
                                  //  idMap.put(contract.id.contractId, contract.id);
                                	  idMap.put("ID", contract.id);
                                });
                    });
        	Map m = g.fromJson(req.body(), Map.class);        	
        	Order.ContractId contractId = (Order.ContractId) idMap.get("ID");         	
        	ExerciseCommand iouCreate = contractId.exerciseInitiate_payment();
            submit(client, req.params("name"), iouCreate);
            String party = req.params("name");
            return "Payment Initialised by "+party;
        }, g::toJson);      
        
        Spark.post("/acceptPayment/:name", (req, res) -> {   	
        	System.out.println(req.body());
        	TransactionFilter iouFilter = filterFor(Payment.TEMPLATE_ID, req.params("name"));       	
            
            BiMap<String, Payment.ContractId> idMap = Maps.synchronizedBiMap(HashBiMap.create());
            AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

            client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                    .blockingForEach(response -> {
                        response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                        response.getCreatedEvents().stream()
                                .map(Payment.Contract::fromCreatedEvent)
                                .forEach(contract -> {                                    
                                  //  idMap.put(contract.id.contractId, contract.id);
                                	  idMap.put("ID", contract.id);
                                });
                    });
        	Map m = g.fromJson(req.body(), Map.class);        	
        	Payment.ContractId contractId = (Payment.ContractId) idMap.get("ID");         	
        	ExerciseCommand iouCreate = contractId.exerciseAccept_payment();
            submit(client, req.params("name"), iouCreate);
            String party = req.params("name");
            return "Payment accepted by "+party;
        }, g::toJson);      
        

    }
    
    
  


	private static Empty submit(LedgerClient client, String party, Command c) {
        return client
            .getCommandSubmissionClient()
            .submit(
                UUID.randomUUID().toString(),
                "IouApp",
                UUID.randomUUID().toString(),
                party,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Collections.singletonList(c))
            .blockingGet();

    }
//*******************************************************************************************************************************************//
//              											Get methods starts from here ...!
//*******************************************************************************************************************************************//  
    
    private static ConcurrentHashMap<String, Deal_Initiate.Contract> getDeal_Initiate(LedgerClient client,String name) {
    	TransactionFilter iouFilter = filterFor(Deal_Initiate.TEMPLATE_ID, name);
    	System.out.println(iouFilter);
    	ConcurrentHashMap<String, Deal_Initiate.Contract> contracts = new ConcurrentHashMap<>();
        AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

        client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                .blockingForEach(response -> {
                    response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                    response.getCreatedEvents().stream()
                            .map(Deal_Initiate.Contract::fromCreatedEvent)
                            .forEach(contract -> {                            	
                            	contracts.put(contract.id.contractId, contract);
                            });
                });
        
         return contracts;
    
    } 
    
    private static ConcurrentHashMap<String, Invoice.Contract> getInvoice(LedgerClient client,String name) {
    	TransactionFilter iouFilter = filterFor(Invoice.TEMPLATE_ID, name);
    	System.out.println(iouFilter);
    	ConcurrentHashMap<String, Invoice.Contract> contracts = new ConcurrentHashMap<>();
        AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

        client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                .blockingForEach(response -> {
                    response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                    response.getCreatedEvents().stream()
                            .map(Invoice.Contract::fromCreatedEvent)
                            .forEach(contract -> {                            	
                            	contracts.put(contract.id.contractId, contract);
                            });
                });
        
         return contracts;
    
    } 
    
    private static ConcurrentHashMap<String, Agreement_order_details.Contract> getAggrementDetails(LedgerClient client,String name) {
    	TransactionFilter iouFilter = filterFor(Agreement_order_details.TEMPLATE_ID, name);
    	System.out.println(iouFilter);
    	ConcurrentHashMap<String, Agreement_order_details.Contract> contracts = new ConcurrentHashMap<>();
        AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

        client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                .blockingForEach(response -> {
                    response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                    response.getCreatedEvents().stream()
                            .map(Agreement_order_details.Contract::fromCreatedEvent)
                            .forEach(contract -> {                            	
                            	contracts.put(contract.id.contractId, contract);
                            });
                });
        
         return contracts;
    
    } 
    
    private static ConcurrentHashMap<String, Order.Contract> getOrderDetails(LedgerClient client,String name) {
    	TransactionFilter iouFilter = filterFor(Order.TEMPLATE_ID, name);
    	System.out.println(iouFilter);
    	ConcurrentHashMap<String, Order.Contract> contracts = new ConcurrentHashMap<>();
        AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

        client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                .blockingForEach(response -> {
                    response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                    response.getCreatedEvents().stream()
                            .map(Order.Contract::fromCreatedEvent)
                            .forEach(contract -> {                            	
                            	contracts.put(contract.id.contractId, contract);
                            });
                });
        
         return contracts;
    } 
    
    private static ConcurrentHashMap<String, Payment.Contract> getPaymentDetails(LedgerClient client,String name) {
    	TransactionFilter iouFilter = filterFor(Payment.TEMPLATE_ID, name);
    	System.out.println(iouFilter);
    	ConcurrentHashMap<String, Payment.Contract> contracts = new ConcurrentHashMap<>();
        AtomicReference<LedgerOffset> acsOffset = new AtomicReference<>(LedgerOffset.LedgerBegin.getInstance());

        client.getActiveContractSetClient().getActiveContracts(iouFilter, true)
                .blockingForEach(response -> {
                    response.getOffset().ifPresent(offset -> acsOffset.set(new LedgerOffset.Absolute(offset)));
                    response.getCreatedEvents().stream()
                            .map(Payment.Contract::fromCreatedEvent)
                            .forEach(contract -> {                            	
                            	contracts.put(contract.id.contractId, contract);
                            });
                });
        
         return contracts;
    
    } 
    

    private static TransactionFilter filterFor(Identifier templateId, String party) {
        InclusiveFilter inclusiveFilter = new InclusiveFilter(Collections.singleton(templateId));
        Map<String, Filter> filter = Collections.singletonMap(party, inclusiveFilter);
        return new FiltersByParty(filter);
    }
    
}










