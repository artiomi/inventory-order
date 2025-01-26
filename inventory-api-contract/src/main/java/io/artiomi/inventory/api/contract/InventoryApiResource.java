package io.artiomi.inventory.api.contract;

import io.artiomi.inventory.api.contract.model.AcquireRequestApi;
import io.artiomi.inventory.api.contract.model.ExceptionResponse;
import io.artiomi.inventory.api.contract.model.InventoryItemApi;
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

@Tag(name = "Inventory resource",
        description = "Rest API endpoints for inventory manipulations")
public interface InventoryApiResource {
    @Operation(summary = "Query existing inventories")
    @Parameters({
            @Parameter(name = "id", description = "Id of an inventory to be queried"),
            @Parameter(name = "name", description = "Name of parameter used for query")})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Return a list of inventories, if none parameter provided" +
                    " returns all the inventories",
                    content = @Content(schema = @Schema(implementation = InventoryItemApi.class))),
            @ApiResponse(responseCode = "400", description = "In case of search failure, returns exception cause",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/inventories/")
    ResponseEntity<List<InventoryItemApi>> search(
            @RequestParam(name = "id", required = false) String id,
            @RequestParam(name = "name", required = false) String name);


    @Operation(summary = "Create new inventory item, or update if exists one with given id")
    @Parameters({
            @Parameter(name = "item", description = "Payload of inventory to be created",
                    content = @Content(schema = @Schema(implementation = InventoryItemApi.class)))}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created inventory item",
                    content = @Content(schema = @Schema(implementation = InventoryItemApi.class))),
            @ApiResponse(responseCode = "400", description = "In case of create failure, returns exception cause",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping("/inventories/")
    ResponseEntity<InventoryItemApi> save(@Valid @RequestBody InventoryItemApi item);


    @Operation(summary = "Acquire a given number of items for given inventory ID")
    @Parameters({
            @Parameter(name = "id", description = "Id of the inventory"),
            @Parameter(name = "acquireRequest", description = "Acquire request payload",
                    content = @Content(schema = @Schema(implementation = AcquireRequestApi.class)))
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns update inventory with deducted items number",
                    content = @Content(schema = @Schema(implementation = InventoryItemApi.class))),
            @ApiResponse(responseCode = "400", description = "In case of acquire failure, returns exception cause",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = "404", description = "Inventory with provided id is missing")
    })
    @PutMapping("/inventories/{id}/acquire/")
    ResponseEntity<InventoryItemApi> acquire(
            @PathVariable(name = "id") String id,
            @Valid @RequestBody AcquireRequestApi acquireRequest);

    @Operation(summary = "Delete inventory with ID")
    @Parameters({
            @Parameter(name = "id", description = "Id of the inventory")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Inventory deleted successfully."),
            @ApiResponse(responseCode = "400", description = "In case of delete failure, returns exception cause",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @DeleteMapping("/inventories/{id}/")
    ResponseEntity<Void> delete(@PathVariable(name = "id") String id);
}