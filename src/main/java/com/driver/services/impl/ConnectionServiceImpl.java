package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ConnectionRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository2;
    @Autowired
    ServiceProviderRepository serviceProviderRepository2;
    @Autowired
    ConnectionRepository connectionRepository2;

    @Override
    public User connect(int userId, String countryName) throws Exception{

        User user = userRepository2.findById(userId).get();


        return user;
    }
    @Override
    public User disconnect(int userId) throws Exception {

        User user = userRepository2.findById(userId).get();



        return user;
    }
    @Override
    public User communicate(int senderId, int receiverId) throws Exception {

        try {
            User sender = userRepository2.findById(senderId).get();
            User receiver = userRepository2.findById(receiverId).get();

            String senderCountryCode, receiverCountryCode;

            senderCountryCode = sender.getOriginalCountry().getCode();

            if (receiver.getConnected()==true) {
                String[] ip = receiver.getMaskedIp().split(".");
                receiverCountryCode = ip[0];
            } else {
                String[] ip = receiver.getOriginalIp().split(".");
                receiverCountryCode = ip[0];
            }

            if (!(senderCountryCode.equals(receiverCountryCode))) {
                connect(senderId, receiver.getOriginalCountry().getCountryName().toString());
            }
            return sender;
        }catch (Exception e){
            throw new Exception("Cannot establish communication");
        }
    }
}