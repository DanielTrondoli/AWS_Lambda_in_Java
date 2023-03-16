package function;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class DynamoDbHandler implements RequestHandler<EmployeeRequest, EmployeeResponse> {

    private AmazonDynamoDB amazonDynamoDB;
    private String DYNAMODB_TABLE_NAME = "employee";
    private Regions REGION = Regions.US_EAST_1;

    public EmployeeResponse handleRequest(EmployeeRequest request, Context context) {
        this.initDynamoDbClient();

        persistData(request);

        return new EmployeeResponse(""+request.getEmail(), 201);
    }

    private void persistData(EmployeeRequest request) throws ConditionalCheckFailedException {

        Map<String, AttributeValue> attributesMap = new HashMap<>();

        attributesMap.put("employeeID", new AttributeValue(String.valueOf(request.getEmployeeID())));
        attributesMap.put("firstName", new AttributeValue(request.getFirstName()));
        attributesMap.put("lastName", new AttributeValue(request.getLastName()));
        attributesMap.put("email", new AttributeValue(String.valueOf(request.getEmail())));
        attributesMap.put("department", new AttributeValue(new Gson().toJson(request.getDepartment())));

        amazonDynamoDB.putItem(DYNAMODB_TABLE_NAME, attributesMap);
    }

    private void initDynamoDbClient() {
        this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withRegion(REGION)
                .build();
    }
}
