create table oauth_client_details (    client_id VARCHAR(256) PRIMARY KEY,    resource_ids VARCHAR(256),    client_secret VARCHAR(256),    scope VARCHAR(256),    authorized_grant_types VARCHAR(256),    web_server_redirect_uri VARCHAR(256),    authorities VARCHAR(256),    access_token_validity INTEGER,    refresh_token_validity INTEGER,    additional_information VARCHAR(4096),    autoapprove VARCHAR(256));
INSERT INTO `security_demo`.`oauth_client_details`(`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('order-client', NULL, '$2a$10$4zd/aj2BNJhuM5PIs5BupO8tiN2yikzP7JMzNaq1fXhcXUefWCOF2', 'all', 'authorization_code,refresh_token,password', 'http://localhost:8082/user/hello', NULL, 3600, 36000, NULL, '1');
INSERT INTO `security_demo`.`oauth_client_details`(`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES ('user-client', NULL, '$2a$10$4zd/aj2BNJhuM5PIs5BupO8tiN2yikzP7JMzNaq1fXhcXUefWCOF2', 'all', 'authorization_code,refresh_token,password', 'http://localhost:8082/user/hello', NULL, 3600, 36000, NULL, '1');