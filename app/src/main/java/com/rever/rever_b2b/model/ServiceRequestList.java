package com.rever.rever_b2b.model;

/**
 * Created by Matheswari on 6/7/2016.
 */
public class ServiceRequestList {

        private String sr_id, sr_no, status, created_on, consumer_name;

        public ServiceRequestList(String sr_id, String sr_no, String status, String created_on, String consumer_name){
            this.sr_id = sr_id; this.sr_no =sr_no; this.status = status; this.created_on = created_on; this.consumer_name = consumer_name;
        }

        public void setSrId(String srId){
            this.sr_id = srId;
        }

        public String getSrId(){
            return sr_id;
        }

        public void setSrNo(String srId){
            this.sr_no = srId;
        }

        public String getSrNo(){
            return sr_no;
        }

        public void setStatus(String status){
            this.status = status;
        }

        public String getStatus(){
            return status;
        }

        public void setCreatedOn(String created_on){
            this.created_on = created_on;
        }

        public String getCreated_on(){
            return created_on;
        }

        public void setConsumerName(String consumer_name){
            this.consumer_name = consumer_name;
        }

        public String getConsumer_name(){
            return consumer_name;
        }


}
