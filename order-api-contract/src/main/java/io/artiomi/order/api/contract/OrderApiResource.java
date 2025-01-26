package io.artiomi.order.api.contract;

import io.artiomi.order.api.contract.model.ExceptionResponse;
import io.artiomi.order.api.contract.model.OrderItemApi;
import io.artiomi.order.api.contract.model.OrderItemUpsertRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order resource",
        description = "Rest API endpoints for orders manipulations")
public interface OrderApiResource {
    @Operation(summary = "Query existing orders")
    @Parameters({
            @Parameter(name = "id", description = "Id of an inventory to be queried"),
            @Parameter(name = "name", description = "Name of parameter used for query"),
            @Parameter(name = "inventoryRef", description = "Id of a existing inventory")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return a list of orders, if none parameter provided" +
                    " returns all the orders",
                    content = @Content(schema = @Schema(implementation = OrderItemApi.class))),
            @ApiResponse(responseCode = "400", description = "In case of search failure, returns exception cause",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/orders/")
    ResponseEntity<List<OrderItemApi>> search(
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "inventoryRef", required = false) String inventoryRef);


    @Operation(summary = "Update order item with given id")
    @Parameters({
            @Parameter(name = "id", description = "Id of an inventory to be updated"),
            @Parameter(name = "request", description = "Payload of order to be updated",
                    content = @Content(schema = @Schema(implementation = OrderItemUpsertRequest.class)))}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated order item",
                    content = @Content(schema = @Schema(implementation = OrderItemApi.class))),
            @ApiResponse(responseCode = "400", description = "In case of update failure, returns exception cause",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PutMapping(value = "/orders/{id}")
    ResponseEntity<OrderItemApi> update(
            @PathVariable(name = "id") String id,
            @Valid @RequestBody OrderItemUpsertRequest request);


    @Operation(summary = "Create new order")
    @Parameters({
            @Parameter(name = "request", description = "Payload of order to be created",
                    content = @Content(schema = @Schema(implementation = OrderItemUpsertRequest.class)))}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created order item",
                    content = @Content(schema = @Schema(implementation = OrderItemApi.class))),
            @ApiResponse(responseCode = "400", description = "In case of create failure, returns exception cause",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping(value = "/orders/")
    ResponseEntity<OrderItemApi> create(@Valid @RequestBody OrderItemUpsertRequest request);
}
