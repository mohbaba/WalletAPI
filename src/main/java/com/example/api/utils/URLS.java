package com.example.api.utils;

import org.springframework.beans.factory.annotation.Value;

public class URLS {
    @Value("${cloud.paystack.key}")
    public static String API_KEY;
    public static final String ACCEPT_PAYMENT_URL = "https://api.paystack.co/transaction/initialize";
    public static final String TRANSFER_RECIPIENT_URL = "https://api.paystack.co/transferrecipient";
    public static final String INITIATE_TRANSFER_URL = "https://api.paystack.co/transfer";
    public static final String VERIFY_PAYMENT_URL = "https://api.paystack.co/transaction/verify/";
    public static final String CREATE_ACCOUNT_URL = "https://api.paystack.co/dedicated_account";
    public static final String VERIFY_PHONE_NUMBER_URL = "https://api.prembly.com/identitypass/verification/phone_number";
    public static final String PREFERRED_BANK = "test-bank";
}
