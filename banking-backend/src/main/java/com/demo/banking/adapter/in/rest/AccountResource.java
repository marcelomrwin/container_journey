package com.demo.banking.adapter.in.rest;

import com.demo.banking.application.port.in.AccountUseCase;
import com.demo.banking.domain.model.Account;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;


import java.math.BigDecimal;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Account Management", description = "Operations related to account management")
public class AccountResource {

    private final AccountUseCase accountUseCase;

    @Inject
    public AccountResource(AccountUseCase accountUseCase) {
        this.accountUseCase = accountUseCase;
    }

    @POST
    @Operation(summary = "Create a new account")
    @APIResponse(responseCode = "201", description = "Account created",
            content = @Content(schema = @Schema(implementation = Account.class)))
    public Response createAccount(Account account) {
        Account created = accountUseCase.createAccount(account);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @GET
    @Operation(summary = "Get all accounts")
    @APIResponse(responseCode = "200", description = "List of all accounts")
    public List<Account> getAllAccounts() {
        return accountUseCase.getAllAccounts();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get account by ID")
    @APIResponse(responseCode = "200", description = "Account found")
    @APIResponse(responseCode = "404", description = "Account not found")
    public Response getAccountById(@PathParam("id") Long id) {
        return accountUseCase.getAccountById(id)
                .map(account -> Response.ok(account).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/number/{accountNumber}")
    @Operation(summary = "Get account by account number")
    @APIResponse(responseCode = "200", description = "Account found")
    @APIResponse(responseCode = "404", description = "Account not found")
    public Response getAccountByNumber(@PathParam("accountNumber") String accountNumber) {
        return accountUseCase.getAccountByNumber(accountNumber)
                .map(account -> Response.ok(account).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an account")
    @APIResponse(responseCode = "200", description = "Account updated")
    @APIResponse(responseCode = "404", description = "Account not found")
    public Response updateAccount(@PathParam("id") Long id, Account account) {
        return accountUseCase.getAccountById(id)
                .map(existingAccount -> {
                    account.setId(id);
                    Account updated = accountUseCase.updateAccount(account);
                    return Response.ok(updated).build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete an account")
    @APIResponse(responseCode = "204", description = "Account deleted")
    @APIResponse(responseCode = "404", description = "Account not found")
    public Response deleteAccount(@PathParam("id") Long id) {
        return accountUseCase.getAccountById(id)
                .map(account -> {
                    accountUseCase.deleteAccount(id);
                    return Response.noContent().build();
                })
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Path("/{accountNumber}/deposit")
    @Operation(summary = "Deposit money to an account")
    @APIResponse(responseCode = "200", description = "Deposit successful")
    @APIResponse(responseCode = "404", description = "Account not found")
    @APIResponse(responseCode = "400", description = "Invalid amount")
    public Response deposit(@PathParam("accountNumber") String accountNumber,
                            @QueryParam("amount") BigDecimal amount) {
        try {
            Account updated = accountUseCase.deposit(accountNumber, amount);
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Path("/{accountNumber}/withdraw")
    @Operation(summary = "Withdraw money from an account")
    @APIResponse(responseCode = "200", description = "Withdrawal successful")
    @APIResponse(responseCode = "404", description = "Account not found")
    @APIResponse(responseCode = "400", description = "Invalid amount or insufficient funds")
    public Response withdraw(@PathParam("accountNumber") String accountNumber,
                             @QueryParam("amount") BigDecimal amount) {
        try {
            Account updated = accountUseCase.withdraw(accountNumber, amount);
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(e.getMessage()))
                    .build();
        }
    }

    public static class ErrorMessage {
        public String message;

        public ErrorMessage(String message) {
            this.message = message;
        }
    }
}