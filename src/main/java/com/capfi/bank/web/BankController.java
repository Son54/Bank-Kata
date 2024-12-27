package com.capfi.bank.web;

import com.capfi.bank.model.OperationAmount;
import com.capfi.bank.model.OperationLine;
import com.capfi.bank.service.BankService;
import com.capfi.bank.service.OperationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bank API")
@RequiredArgsConstructor
@RestController
public class BankController {

    private final BankService bankService;

    @Operation(summary = "Return operation history", description = "Return operation history")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operation list",
            content = {@Content(mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = OperationLine.class)))})

    })
    @GetMapping("/history")
    public List<OperationLine> history() {
        return bankService.history();
    }

    @Operation(summary = "Deposit money in the account", description = "Deposit money in the account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operation Line",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = OperationLine.class))}),

    })
    @PostMapping("/deposit")
    public OperationLine deposit(@RequestBody @Valid OperationAmount creditAmount) {
        return bankService.deposit(creditAmount);
    }

    @Operation(summary = "Withdraw amount from the account", description = "Withdraw amount from the account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operation Line",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = OperationLine.class))}),
        @ApiResponse(responseCode = "405", description = "Not enough money in the account",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = Error.class))}),
        @ApiResponse(responseCode = "405", description = "No money in the account",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = Error.class))})
    })
    @PostMapping("/withdraw")
    public OperationLine withdraw(@RequestBody @Valid OperationAmount withdrawAmount) throws OperationException {
        return bankService.withdraw(withdrawAmount);
    }

}
