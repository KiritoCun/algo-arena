IF OBJECT_ID('sys_tenant', 'U') IS NOT NULL
  DROP TABLE sys_tenant;
GO
CREATE TABLE sys_tenant
(
    id                    bigint                          NOT NULL,
    tenant_id             nvarchar(20)                    NOT NULL,
    contact_user_name     nvarchar(255)                   NULL,
    contact_phone         nvarchar(20)                    NULL,
    company_name          nvarchar(255)                   NULL,
    license_number        nvarchar(30)                    NULL,
    address               nvarchar(200)                   NULL,
    intro                 nvarchar(200)                   NULL,
    domain                nvarchar(200)                   NULL,
    remark                nvarchar(200)                   NULL,
    package_id            bigint                          NULL,
    expire_time           datetime2(7)                    NULL,
    account_count         int             DEFAULT ((-1))  NULL,
    status                nchar(1)        DEFAULT ('0')   NULL,
    del_flag              nchar(1)        DEFAULT ('0')   NULL,
    create_dept           bigint                          NULL,
    create_by             bigint                          NULL,
    create_time           datetime2(7)                    NULL,
    update_by             bigint                          NULL,
    update_time           datetime2(7)                    NULL,
    CONSTRAINT PK__sys_tenant__B21E8F2427725F8A PRIMARY KEY CLUSTERED (id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_tenant VALUES (1, N'000000', N'Management group', N'0969753414', N'XXX Limited', NULL, NULL, N'Multi-tenant general background management system', NULL, NULL, NULL, NULL, -1, N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO

IF OBJECT_ID('sys_tenant_package', 'U') IS NOT NULL
  DROP TABLE sys_tenant_package;
GO
CREATE TABLE sys_tenant_package
(
    package_id            bigint                          NOT NULL,
    package_name          nvarchar(20)                    NOT NULL,
    menu_ids              nvarchar(20)                    NULL,
    remark                nvarchar(200)                   NULL,
    menu_check_strictly   tinyint         DEFAULT ((1))   NULL,
    status                nchar(1)        DEFAULT ('0')   NULL,
    del_flag              nchar(1)        DEFAULT ('0')   NULL,
    create_dept           bigint                          NULL,
    create_by             bigint                          NULL,
    create_time           datetime2(7)                    NULL,
    update_by             bigint                          NULL,
    update_time           datetime2(7)                    NULL,
    CONSTRAINT PK__sys_tenant_package__B21E8F2427725F8A PRIMARY KEY CLUSTERED (package_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('sys_dept', 'U') IS NOT NULL
  DROP TABLE sys_dept;
GO
CREATE TABLE sys_dept
(
    dept_id     bigint                     NOT NULL,
    tenant_id   nvarchar(20) DEFAULT ('000000') NULL,
    parent_id   bigint       DEFAULT ((0)) NULL,
    ancestors   nvarchar(500)DEFAULT ''    NULL,
    dept_name   nvarchar(255) DEFAULT ''    NULL,
    order_num   int          DEFAULT ((0)) NULL,
    leader      nvarchar(20)               NULL,
    phone       nvarchar(11)               NULL,
    email       nvarchar(255)               NULL,
    status      nchar(1)     DEFAULT ('0') NULL,
    del_flag    nchar(1)     DEFAULT ('0') NULL,
    create_dept bigint                     NULL,
    create_by   bigint                     NULL,
    create_time datetime2(7)               NULL,
    update_by   bigint                     NULL,
    update_time datetime2(7)               NULL,
    CONSTRAINT PK__sys_dept__DCA659747DE13804 PRIMARY KEY CLUSTERED (dept_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_dept VALUES (100, N'000000', 0, N'0', N'Algorithm Arena', 0, N'Administrator', N'0969753414', N'algo-arena@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO
INSERT sys_dept VALUES (101, N'000000', 100, N'0,100', N'Quản lý bài thi', 1, N'Nguyễn Ngọc Phú', N'0775545294', N'phunguyen@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO
INSERT sys_dept VALUES (102, N'000000', 100, N'0,100', N'Quản lý bài toán', 2, N'Nguyễn Văn Cường', N'0346844010', N'cuongnguyen@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO
INSERT sys_dept VALUES (103, N'000000', 100, N'0,100', N'Quản lý bài làm', 3, N'Trần Văn Hữu', N'0383609548', N'huutran@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO
INSERT sys_dept VALUES (104, N'000000', 100, N'0,100', N'Quản lý doanh thu', 4, N'Nguyễn Minh Quốc', N'0933071554', N'quocnguyen@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO
INSERT sys_dept VALUES (105, N'000000', 100, N'0,100', N'Giảng viên', 5, N'Nguyễn Minh Quốc', N'0933071554', N'quocnguyen@gmail.com', N'0', N'0', 103, 1, getdate(), NULL, NULL)
GO

IF OBJECT_ID('sys_notice', 'U') IS NOT NULL
  DROP TABLE sys_notice;
GO
CREATE TABLE sys_notice
(
    notice_id      bigint                     NOT NULL,
    tenant_id      nvarchar(20) DEFAULT ('000000') NULL,
    notice_title   nvarchar(255)               NOT NULL,
    notice_type    nchar(1)                   NOT NULL,
    notice_content nvarchar(max)              NULL,
    status         nchar(1)     DEFAULT ('0') NULL,
    create_dept    bigint                     NULL,
    create_by      bigint                     NULL,
    create_time    datetime2(7)               NULL,
    update_by      bigint                     NULL,
    update_time    datetime2(7)               NULL,
    remark         nvarchar(255)              NULL,
    CONSTRAINT PK__sys_noti__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (notice_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
TEXTIMAGE_ON [PRIMARY]
GO

INSERT sys_notice VALUES (1, N'000000', N'Reminder: 2023-11-20 A new version has been released', N'3', N'New version content', N'0', 103, 1, getdate(), NULL, NULL, N'administrator')
GO
INSERT sys_notice VALUES (2, N'000000', N'Maintenance notice: 2023-11-01 Algorithm Arena system maintenance in the early morning', N'3', N'Maintenance content', N'0', 103, 1, getdate(), NULL, NULL, N'administrator')
GO

IF OBJECT_ID('sys_post', 'U') IS NOT NULL
  DROP TABLE sys_post;
GO
CREATE TABLE sys_post
(
    post_id     bigint                          NOT NULL,
    tenant_id   nvarchar(20) DEFAULT ('000000') NULL,
    post_code   nvarchar(64)                    NOT NULL,
    post_name   nvarchar(255)                   NOT NULL,
    post_sort   int                             NOT NULL,
    status      nchar(1)                        NOT NULL,
    create_dept bigint                          NULL,
    create_by   bigint                          NULL,
    create_time datetime2(7)                    NULL,
    update_by   bigint                          NULL,
    update_time datetime2(7)                    NULL,
    remark      nvarchar(500)                   NULL,
    CONSTRAINT PK__sys_post__3ED7876668E2D081 PRIMARY KEY CLUSTERED (post_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_post VALUES (1, N'000000', N'super-management', N'Quản lý cấp cao', 1, N'0', 103, 1, getdate(), NULL, NULL, N'')
GO
INSERT sys_post VALUES (2, N'000000', N'management', N'Quản lý', 2, N'0', 103, 1, getdate(), NULL, NULL, N'')
GO
INSERT sys_post VALUES (3, N'000000', N'staff', N'Nhân viên', 3, N'0', 103, 1, getdate(), NULL, NULL, N'')
GO

IF OBJECT_ID('sys_user', 'U') IS NOT NULL
  DROP TABLE sys_user;
GO
CREATE TABLE sys_user
(
    user_id     bigint                             NOT NULL,
    tenant_id   nvarchar(20)  DEFAULT ('000000')   NULL,
    dept_id     bigint                             NULL,
    user_name   nvarchar(40)                       NOT NULL,
    nick_name   nvarchar(255)                      NOT NULL,
    user_type   nvarchar(10)  DEFAULT ('sys_user') NULL,
    email       nvarchar(50)  DEFAULT ''           NULL,
    phonenumber nvarchar(11)  DEFAULT ''           NULL,
    sex         nchar(1)      DEFAULT ('0')        NULL,
    avatar      bigint                             NULL,
    password    nvarchar(100) DEFAULT ''           NULL,
    status      nchar(1)      DEFAULT ('0')        NULL,
    del_flag    nchar(1)      DEFAULT ('0')        NULL,
    login_ip    nvarchar(128) DEFAULT ''           NULL,
    login_date  datetime2(7)                       NULL,
    create_dept bigint                             NULL,
    create_by   bigint                             NULL,
    create_time datetime2(7)                       NULL,
    update_by   bigint                             NULL,
    update_time datetime2(7)                       NULL,
    remark      nvarchar(500)                      NULL,
    system_type nvarchar(30)  DEFAULT ('system')   NOT NULL,
    CONSTRAINT PK__sys_user__B9BE370F79170B6A PRIMARY KEY CLUSTERED (user_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_user VALUES (1, N'000000', 100, N'admin', N'Administrator', N'sys_user', N'admin@gmail.com', N'0987654321', N'1', NULL, N'$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'administrator', N'system')
GO
INSERT sys_user VALUES (2, N'000000', 101, N'hoald', N'Lê Đình Hòa', N'sys_user', N'hoald@gmail.com', N'0999999999', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
GO
INSERT sys_user VALUES (3, N'000000', 102, N'dongbd', N'Bùi Đức Đồng', N'sys_user', N'dongbd@gmail.com', N'0888888888', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
GO
INSERT sys_user VALUES (4, N'000000', 103, N'vunq', N'Nguyễn Quốc Vũ', N'sys_user', N'vunq@gmail.com', N'0777777777', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
GO
INSERT sys_user VALUES (5, N'000000', 104, N'huyenvtk', N'Võ Thị Khánh Huyền', N'sys_user', N'huyenvtk@gmail.com', N'0666666666', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
GO
INSERT sys_user VALUES (6, N'000000', 105, N'nguyennd', N'Nguyễn Đăng Khảm', N'sys_user', N'khamnd@gmail.com', N'0123456789', N'1', NULL, N'$2a$10$ASuKduzRhpJdv2be3MQWi.PIxMeNMokIfKJLWc2cP3k0sVhXEtb0K', N'0', N'0', N'127.0.0.1', getdate(), 103, 1, getdate(), NULL, NULL, N'', N'system')
GO

IF OBJECT_ID('sys_logininfor', 'U') IS NOT NULL
  DROP TABLE sys_logininfor;
GO
CREATE TABLE sys_logininfor
(
    info_id        bigint                      NOT NULL,
    tenant_id      nvarchar(20)  DEFAULT ('000000') NULL,
    user_name      nvarchar(255)  DEFAULT ''    NULL,
    ipaddr         nvarchar(128) DEFAULT ''    NULL,
    login_location nvarchar(255) DEFAULT ''    NULL,
    browser        nvarchar(50)  DEFAULT ''    NULL,
    os             nvarchar(50)  DEFAULT ''    NULL,
    status         nchar(1)      DEFAULT ('0') NULL,
    msg            nvarchar(255) DEFAULT ''    NULL,
    login_time     datetime2(7)                NULL,
    CONSTRAINT PK__sys_logi__3D8A9C1A1854AE10 PRIMARY KEY CLUSTERED (info_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX idx_sys_logininfor_s ON sys_logininfor (status)
GO
CREATE NONCLUSTERED INDEX idx_sys_logininfor_lt ON sys_logininfor (login_time)
GO

IF OBJECT_ID('sys_user_post', 'U') IS NOT NULL
  DROP TABLE sys_user_post;
GO
CREATE TABLE sys_user_post
(
    user_id bigint NOT NULL,
    post_id bigint NOT NULL,
    CONSTRAINT PK__sys_user__CA534F799C04589B PRIMARY KEY CLUSTERED (user_id, post_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_user_post VALUES (2, 1)
GO
INSERT sys_user_post VALUES (3, 1)
GO
INSERT sys_user_post VALUES (4, 1)
GO

IF OBJECT_ID('sys_user_role', 'U') IS NOT NULL
  DROP TABLE sys_user_role;
GO
CREATE TABLE sys_user_role
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT PK__sys_user__6EDEA153FB34D8F0 PRIMARY KEY CLUSTERED (user_id, role_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT sys_user_role VALUES (1, 1)
GO
INSERT sys_user_role VALUES (2, 2)
GO
INSERT sys_user_role VALUES (3, 3)
GO
INSERT sys_user_role VALUES (4, 4)
GO
INSERT sys_user_role VALUES (5, 5)
GO
INSERT sys_user_role VALUES (6, 6)
GO

IF OBJECT_ID('sys_oss', 'U') IS NOT NULL
  DROP TABLE sys_oss;
GO
CREATE TABLE sys_oss
(
    oss_id        bigint                          NOT NULL,
    tenant_id     nvarchar(20)  DEFAULT ('000000') NULL,
    file_name     nvarchar(255) DEFAULT ''        NOT NULL,
    original_name nvarchar(255) DEFAULT ''        NOT NULL,
    file_suffix   nvarchar(10)  DEFAULT ''        NOT NULL,
    url           nvarchar(500)                   NOT NULL,
    create_dept   bigint                          NULL,
    create_time   datetime2(7)                    NULL,
    create_by     bigint                          NULL,
    update_time   datetime2(7)                    NULL,
    update_by     bigint                          NULL,
    service       nvarchar(20)  DEFAULT ('minio') NOT NULL,
    CONSTRAINT PK__sys_oss__91241EA442389F0D PRIMARY KEY CLUSTERED (oss_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('sys_oss_config', 'U') IS NOT NULL
  DROP TABLE sys_oss_config;
GO
CREATE TABLE sys_oss_config
(
    oss_config_id bigint                      NOT NULL,
    tenant_id     nvarchar(20)  DEFAULT ('000000') NULL,
    config_key    nvarchar(20)  DEFAULT ''    NOT NULL,
    access_key    nvarchar(255) DEFAULT ''    NULL,
    secret_key    nvarchar(255) DEFAULT ''    NULL,
    bucket_name   nvarchar(255) DEFAULT ''    NULL,
    prefix        nvarchar(255) DEFAULT ''    NULL,
    endpoint      nvarchar(255) DEFAULT ''    NULL,
    domain        nvarchar(255) DEFAULT ''    NULL,
    is_https      nchar(1)      DEFAULT ('N') NULL,
    region        nvarchar(255) DEFAULT ''    NULL,
    access_policy nchar(1)      DEFAULT ('1') NOT NULL,
    status        nchar(1)      DEFAULT ('1') NULL,
    ext1          nvarchar(255) DEFAULT ''    NULL,
    create_dept   bigint                      NULL,
    create_by     bigint                      NULL,
    create_time   datetime2(7)                NULL,
    update_by     bigint                      NULL,
    update_time   datetime2(7)                NULL,
    remark        nvarchar(500)               NULL,
    CONSTRAINT PK__sys_oss___BFBDE87009ED2882 PRIMARY KEY CLUSTERED (oss_config_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

INSERT INTO sys_oss_config VALUES (N'1', N'000000', N'minio', N'admin', N'admin123', N'admin', N'', N'127.0.0.1:9000', N'127.0.0.1:9000',N'N', N'', N'1', N'0', N'', 103, 1, getdate(), 1, getdate(), NULL)
GO
INSERT INTO sys_oss_config VALUES (N'2', N'000000', N'public_document', N'admin', N'admin123', N'admin', N'', N'127.0.0.1:9000', N'127.0.0.1:9000',N'N', N'', N'1', N'0', N'', 103, 1, getdate(), 1, getdate(), NULL)
GO

IF OBJECT_ID('sys_data_history', 'U') IS NOT NULL
    DROP TABLE sys_data_history;
GO
CREATE TABLE sys_data_history
(
    id            bigint         IDENTITY(1,1)      NOT NULL,
    tenant_id     nvarchar(20)   DEFAULT ('000000')     NULL,
    ref_id        bigint                            NOT NULL,
    new_value     nvarchar(1000) DEFAULT ''             NULL,
    old_value     nvarchar(1000) DEFAULT ''             NULL,
    data_field    nvarchar(50)   DEFAULT ''             NULL,
    hist_type     nvarchar(20)   DEFAULT ''         NOT NULL,
    table_name    nvarchar(50)   DEFAULT ''         NOT NULL,
    system_type   nvarchar(30)   DEFAULT ('system') NOT NULL,
    create_dept   bigint         DEFAULT (103)      NOT NULL,
    create_by     bigint         DEFAULT (1)        NOT NULL,
    create_time   datetime2(7)                      NOT NULL,
    update_by     bigint                                NULL,
    update_time   datetime2(7)                          NULL,
    CONSTRAINT PK__sys_data_history PRIMARY KEY CLUSTERED (id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('sys_document', 'U') IS NOT NULL
    DROP TABLE sys_document;
GO
CREATE TABLE sys_document
(
    id                  bigint                              NOT NULL,
    tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
    doc_type            nvarchar(20)                        NULL,
    doc_title           nvarchar(255)                       NULL,
    doc_url             nvarchar(255)                       NULL,
    doc_description     nvarchar(255)                       NULL,
    doc_target          nvarchar(255)                       NULL,
    image_type          nvarchar(50)                        NULL,
    is_external_link    nchar(1)     DEFAULT ('0')          NULL,
    status              nchar(1)     DEFAULT ('0')          NULL,
    oss_id              bigint                              NULL,
    create_dept         bigint                              NOT NULL,
    create_by           bigint                              NOT NULL,
    create_time         datetime2(7)                        NOT NULL,
    update_by           bigint                              NULL,
    update_time         datetime2(7)                        NULL,
    remark              nvarchar(500)                       NULL,
    CONSTRAINT PK__sys_document PRIMARY KEY CLUSTERED (id)
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
)
ON [PRIMARY]
GO

EXEC sys.sp_addextendedproperty
    'MS_Description', N'System document id',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_document',
    'COLUMN', N'id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'Tenant ID',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_document',
    'COLUMN', N'tenant_id'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'Create department',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_document',
    'COLUMN', N'create_dept'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'Creator',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_document',
    'COLUMN', N'create_by'
GO
EXEC sp_addextendedproperty
    'MS_Description', N'Creation time',
    'SCHEMA', N'dbo',
    'TABLE', N'sys_document',
    'COLUMN', N'create_time'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'Remark' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_document',
    'COLUMN', N'remark'
GO
EXEC sys.sp_addextendedproperty
    'MS_Description', N'System document' ,
    'SCHEMA', N'dbo',
    'TABLE', N'sys_document'
GO

IF OBJECT_ID('sys_otp', 'U') IS NOT NULL
  DROP TABLE sys_otp;
GO
CREATE TABLE sys_otp
(
    id              bigint                              NOT NULL,
    tenant_id       nvarchar(20)    DEFAULT ('000000')  NULL,
    otp_code        nvarchar(10)                        NOT NULL,
    ref_id          bigint                              NOT NULL,
    phonenumber     nvarchar(11)  DEFAULT ''            NULL,
    expired_time    datetime2(7)                        NULL,
    otp_type        nvarchar(2)                         NULL,
    status          nchar(1)     DEFAULT ('0')          NULL,
    create_dept     bigint                              NULL,
    create_by       bigint                              NULL,
    create_time     datetime2(7)                        NULL,
    update_by       bigint                              NULL,
    update_time     datetime2(7)                        NULL,
    remark          nvarchar(255)                       NULL,
    system_type     nvarchar(30)  DEFAULT ('system')    NOT NULL,
    CONSTRAINT PK__sys_otp__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

/******************************** ADDITION TABLES ********************************/

IF OBJECT_ID('problem', 'U') IS NOT NULL
  DROP TABLE problem;
GO
GO
CREATE TABLE problem
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  exam_id             bigint                              NULL,
  title               nvarchar(255)                       NOT NULL,
  description         nvarchar(max)                       NOT NULL,
  difficulty          tinyint                             NOT NULL,
  key_path            nvarchar(100)                       NULL,
  category            nvarchar(100)                       NULL,
  video_id            nvarchar(100)                       NULL,
  max_score           tinyint                             NULL,
  number_testcase     tinyint                             NOT NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__problem__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('testcase', 'U') IS NOT NULL
  DROP TABLE testcase;
GO
CREATE TABLE testcase
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  problem_id          bigint                              NOT NULL,
  testcase_json       nvarchar(255)                       NULL,
  illustration        varchar(500)                        NULL,
  oss_id              bigint                              NULL,
  explanation         nvarchar(255)                       NULL,
  is_hidden           tinyint                             NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__testcase__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('exam', 'U') IS NOT NULL
  DROP TABLE exam;
GO
CREATE TABLE exam
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  title               nvarchar(255)                       NOT NULL,
  description         nvarchar(500)                       NOT NULL,
  start_time          datetime2(7)                        NULL,
  end_time            datetime2(7)                        NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__exam__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('customer', 'U') IS NOT NULL
  DROP TABLE customer;
GO
CREATE TABLE customer
(
  user_id             bigint                              NOT NULL,
  tenant_id           nvarchar(20)  DEFAULT ('000000')    NULL,
  customer_type       nvarchar(20)  DEFAULT ('sys_customer')  NULL,
  user_name           nvarchar(40)                        NOT NULL,
  password            nvarchar(100) DEFAULT ''            NULL,
  nick_name           nvarchar(255)                       NOT NULL,
  email               nvarchar(50)  DEFAULT ''            NULL,
  phonenumber         nvarchar(11)  DEFAULT ''            NULL,
  sex                 nchar(1)      DEFAULT ('0')         NULL,
  avatar              bigint                              NULL,
  status              nchar(1)      DEFAULT ('0')         NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(500)                       NULL,
  CONSTRAINT PK__customer__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (user_id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('submission', 'U') IS NOT NULL
  DROP TABLE submission;
GO
CREATE TABLE submission
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  exam_id             bigint                              NULL,
  problem_id          bigint                              NOT NULL,
  user_id             bigint                              NOT NULL,
  code                ntext                               NOT NULL,
  status              tinyint                             NOT NULL,
  score               tinyint                             NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__submission__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('submission_testcase', 'U') IS NOT NULL
  DROP TABLE submission_testcase;
GO
CREATE TABLE submission_testcase
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  submission_id       bigint                              NOT NULL,
  testcase_id         bigint                              NOT NULL,
  actual_output       nvarchar(255)                       NOT NULL,
  is_correct          bit                                 NOT NULL,
  executed_time       datetime2(7)                        NOT NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__submission_testcase__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('exam_result', 'U') IS NOT NULL
  DROP TABLE exam_result;
GO
CREATE TABLE exam_result
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  exam_id             bigint                              NOT NULL,
  user_id             bigint                              NOT NULL,
  total_score         tinyint                             NOT NULL,
  completed_time      datetime2(7)                        NOT NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__seat__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('exam_result_detail', 'U') IS NOT NULL
  DROP TABLE exam_result_detail;
GO
CREATE TABLE exam_result_detail
(
  id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)    DEFAULT ('000000')  NULL,
  exam_id             bigint                              NOT NULL,
  exam_result_id      bigint                              NOT NULL,
  problem_id          bigint                              NOT NULL,
  score               tinyint                             NOT NULL,
  completed_time      varchar(5)                          NOT NULL,
  create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(255)                       NULL,
  CONSTRAINT PK__exam_result_detail__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
      WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
      ON [PRIMARY]
)
ON [PRIMARY]
GO

IF OBJECT_ID('slide', 'U') IS NOT NULL
  DROP TABLE slide;
GO
CREATE TABLE slide
(
	id                  bigint                              NOT NULL,
  tenant_id           nvarchar(20)  DEFAULT ('000000')    NULL,
	slide_url	nvarchar(255)                       NULL,
	create_dept         bigint                              NULL,
  create_by           bigint                              NULL,
  create_time         datetime2(7)                        NULL,
  update_by           bigint                              NULL,
  update_time         datetime2(7)                        NULL,
  remark              nvarchar(500)                       NULL,
	CONSTRAINT PK__slide__3E82A5DB0EC94801 PRIMARY KEY CLUSTERED (id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO
INSERT slide VALUES (1, N'000000', N'https://starlight.vn/Areas/Admin/Content/Fileuploads/images/Slider/hai.jpg', 103, 1, getdate(), NULL, NULL, 'TEST')
GO 
INSERT slide VALUES (2, N'000000', N'https://starlight.vn/Areas/Admin/Content/Fileuploads/images/Slider/z4831004558276_212eb4b5be997c49f8cb6bde9f02bab2.jpg', 103, 1, getdate(), NULL, NULL, 'TEST')
GO
INSERT slide VALUES (3, N'000000', N'https://starlight.vn/Areas/Admin/Content/Fileuploads/images/Slider/z4779781711597_672fa1fd6d3ec4549854486393104032(1).jpg', 103, 1, getdate(), NULL, NULL, 'TEST')
GO
INSERT slide VALUES (4, N'000000', N'https://starlight.vn/Areas/Admin/Content/Fileuploads/images/Slider/nguoi%20vo%20cuoi%20cung.jpg', 103, 1, getdate(), NULL, NULL, 'TEST')
GO

IF OBJECT_ID('sys_oper_log', 'U') IS NOT NULL
  DROP TABLE sys_oper_log;
GO
CREATE TABLE sys_oper_log
(
    oper_id        bigint                       NOT NULL,
    tenant_id      nvarchar(20)   DEFAULT ('000000') NULL,
    title          nvarchar(255)   DEFAULT ''    NULL,
    business_type  int            DEFAULT ((0)) NULL,
    method         nvarchar(100)  DEFAULT ''    NULL,
    request_method nvarchar(10)   DEFAULT ''    NULL,
    operator_type  int            DEFAULT ((0)) NULL,
    oper_name      nvarchar(255)   DEFAULT ''    NULL,
    dept_name      nvarchar(255)   DEFAULT ''    NULL,
    oper_url       nvarchar(255)  DEFAULT ''    NULL,
    oper_ip        nvarchar(128)  DEFAULT ''    NULL,
    oper_location  nvarchar(255)  DEFAULT ''    NULL,
    oper_param     nvarchar(2000) DEFAULT ''    NULL,
    json_result    nvarchar(2000) DEFAULT ''    NULL,
    system_type    nvarchar(30)   DEFAULT ('system')    NOT NULL,
    status         int            DEFAULT ((0)) NULL,
    error_msg      nvarchar(2000) DEFAULT ''    NULL,
    oper_time      datetime2(7)                 NULL,
    cost_time      bigint         DEFAULT ((0)) NULL,
    CONSTRAINT PK__sys_oper__34723BF9BD954573 PRIMARY KEY CLUSTERED (oper_id)
        WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
        ON [PRIMARY]
)
ON [PRIMARY]
GO

CREATE NONCLUSTERED INDEX idx_sys_oper_log_bt ON sys_oper_log (business_type)
GO
CREATE NONCLUSTERED INDEX idx_sys_oper_log_s ON sys_oper_log (status)
GO
CREATE NONCLUSTERED INDEX idx_sys_oper_log_ot ON sys_oper_log (oper_time)
GO

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(1,
'Two Sum',
'<div class="problem-content">
  <h1>Two Sum</h1>
  <p>Given an array of integers <code>nums</code> and an integer <code>target</code>, return indices of the two numbers such that they add up to <code>target</code>.</p>
  <p>You may assume that each input would have exactly one solution, and you may not use the same element twice.</p>
  <p>You can return the answer in any order.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> nums = [2,7,11,15], target = 9
<strong>Output:</strong> [0,1]
<strong>Explanation:</strong> Because nums[0] + nums[1] == 9, we return [0, 1].</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> nums = [3,2,4], target = 6
<strong>Output:</strong> [1,2]</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> nums = [3,3], target = 6
<strong>Output:</strong> [0,1]</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>2 &lt;= nums.length &lt;= 10<sup>4</sup></code></li>
    <li><code>-10<sup>9</sup> &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
    <li><code>-10<sup>9</sup> &lt;= target &lt;= 10<sup>9</sup></code></li>
    <li>Only one valid answer exists.</li>
  </ul>
</div>',
1,
'two-sum',
'Array',
'8-k1C6ehKuw',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(1, 1, '{ "nums": [2, 7, 11, 15], "target": 9, "expect": [0, 1] }', NULL, NULL, 0, SYSDATETIME()),
(2, 1, '{ "nums": [3, 2, 4], "target": 6, "expect": [1, 2] }', NULL, NULL, 0, SYSDATETIME()),
(3, 1, '{ "nums": [3, 3], "target": 6, "expect": [0, 1] }', NULL, NULL, 0, SYSDATETIME()),
(4, 1, '{ "nums": [1, 5, 3, 6, 7], "target": 10, "expect": [2, 4] }', NULL, NULL, 1, SYSDATETIME()),
(5, 1, '{ "nums": [0, 4, 3, 0], "target": 0, "expect": [0, 3] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(2,
'Reverse Linked List',
'<div class="problem-content">
  <h1>Reverse Linked List</h1>
  <p>Given the head of a singly linked list, reverse the list, and return the reversed list.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> head = [1,2,3,4,5]
<strong>Output:</strong> [5,4,3,2,1]</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> head = [1,2,3]
<strong>Output:</strong> [3,2,1]</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> head = [1]
<strong>Output:</strong> [1]</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>1 &lt;= head.length &lt;= 10<sup>4</sup></code></li>
    <li><code>-5000 &lt;= head[i] &lt;= 5000</code></li>
  </ul>
</div>',
3,
'reverse-linked-list',
'Linked List',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(6, 2, '{ "head": [1, 2, 3, 4, 5], "expect": [5, 4, 3, 2, 1] }', NULL, NULL, 0, SYSDATETIME()),
(7, 2, '{ "head": [1, 2], "expect": [2, 1] }', NULL, NULL, 0, SYSDATETIME()),
(8, 2, '{ "head": [1], "expect": [1] }', NULL, NULL, 0, SYSDATETIME()),
(9, 2, '{ "head": [1, 2, 3], "expect": [3, 2, 1] }', NULL, NULL, 1, SYSDATETIME()),
(10, 2, '{ "head": [5, 4, 3, 2, 1], "expect": [1, 2, 3, 4, 5] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(3,
'Jump Game',
'<div class="problem-content">
  <h1>Jump Game</h1>
  <p>You are given an integer array <code>nums</code>. You are initially positioned at the first index and each element in the array represents your maximum jump length at that position.</p>
  <p>Return <strong>true</strong> if you can reach the last index, or <strong>false</strong> otherwise.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> nums = [2,3,1,1,4]
<strong>Output:</strong> true</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> nums = [3,2,1,0,4]
<strong>Output:</strong> false
<strong>Explanation:</strong> Jump 1 step from index 0 to 1, then 3 steps to the last index.</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>1 &lt;= nums.length &lt;= 10<sup>4</sup></code></li>
    <li><code>0 &lt;= nums[i] &lt;= 10<sup>5</sup></code></li>
  </ul>
</div>',
2,
'jump-game',
'Dynamic Programming',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(11, 3, '{ "nums": [2, 3, 1, 1, 4], "expect": true }', NULL, NULL, 0, SYSDATETIME()),
(12, 3, '{ "nums": [3, 2, 1, 0, 4], "expect": false }', NULL, NULL, 0, SYSDATETIME()),
(13, 3, '{ "nums": [0], "expect": true }', NULL, NULL, 0, SYSDATETIME()),
(14, 3, '{ "nums": [1, 2, 3], "expect": true }', NULL, NULL, 1, SYSDATETIME()),
(15, 3, '{ "nums": [1, 0, 0], "expect": false }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(4,
'Valid Parentheses',
'<div class="problem-content">
  <h1>Valid Parentheses</h1>
  <p>Given a string <code>s</code> containing just the characters <code>(</code>, <code>)</code>, <code>{</code>, <code>}</code>, <code>[</code>, and <code>]</code>, determine if the input string is valid.</p>
  <p>An input string is valid if:</p>
  <ul>
    <li>Open brackets must be closed by the same type of brackets.</li>
    <li>Open brackets must be closed in the correct order.</li>
    <li>Every close bracket has a corresponding open bracket of the same type.</li>
  </ul>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> s = "()"
<strong>Output:</strong> true</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> s = "()[]{}"
<strong>Output:</strong> true</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> s = "(]"
<strong>Output:</strong> false</code></pre>
  <h3 class="section-title">Example 4:</h3>
  <pre><code><strong>Input:</strong> s = "([)]"
<strong>Output:</strong> false</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>1 &lt;= s.length &lt;= 10<sup>4</sup></code></li>
    <li><code>s[i]</code> is one of <code>"(", ")", "{", "}", "[", "]"</code>.</li>
  </ul>
</div>',
1,
'valid-parentheses',
'Stack',
'xty7fr-k0TU',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(16, 4, '{ "s": "()", "expect": true }', NULL, NULL, 0, SYSDATETIME()),
(17, 4, '{ "s": "()[]{}", "expect": true }', NULL, NULL, 0, SYSDATETIME()),
(18, 4, '{ "s": "(]", "expect": false }', NULL, NULL, 0, SYSDATETIME()),
(19, 4, '{ "s": "([)]", "expect": false }', NULL, NULL, 1, SYSDATETIME()),
(20, 4, '{ "s": "{[]}", "expect": true }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(5,
'Search a 2D Matrix',
'<div class="problem-content">
  <h1>Search a 2D Matrix</h1>
  <p>Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:</p>
  <ul>
    <li>Integers in each row are sorted from left to right.</li>
    <li>The first integer of each row is greater than the last integer of the previous row.</li>
  </ul>
  <p>Given matrix, an m x n matrix, and <code>target</code>, return <strong>true</strong> if target is in the matrix, and <strong>false</strong> otherwise.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
<strong>Output:</strong> true</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
<strong>Output:</strong> false</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> matrix = [[1]], target = 1
<strong>Output:</strong> true</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>m == matrix.length</code></li>
    <li><code>n == matrix[i].length</code></li>
    <li><code>1 &lt;= m, n &lt;= 100</code></li>
    <li><code>-10<sup>4</sup> &lt;= matrix[i][j], target &lt;= 10<sup>4</sup></code></li>
  </ul>
</div>',
2,
'search-a-2d-matrix',
'Binary Search',
'ZfFl4torNg4',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(21, 5, '{ "matrix": [[1, 4, 7, 11], [2, 5, 8, 12], [3, 6, 9, 16], [10, 13, 14, 17]], "target": 5, "expect": true }', NULL, NULL, 0, SYSDATETIME()),
(22, 5, '{ "matrix": [[1, 4, 7, 11], [2, 5, 8, 12], [3, 6, 9, 16], [10, 13, 14, 17]], "target": 20, "expect": false }', NULL, NULL, 0, SYSDATETIME()),
(23, 5, '{ "matrix": [[1, 4, 7, 11], [2, 5, 8, 12], [3, 6, 9, 16], [10, 13, 14, 17]], "target": 3, "expect": true }', NULL, NULL, 0, SYSDATETIME()),
(24, 5, '{ "matrix": [[-5]], "target": -5, "expect": true }', NULL, NULL, 1, SYSDATETIME()),
(25, 5, '{ "matrix": [[1]], "target": 0, "expect": false }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(6,
'Longest Palindromic Substring',
'<div class="problem-content">
  <h1>Longest Palindromic Substring</h1>
  <p>Given a string <code>s</code>, return the longest palindromic substring in <code>s</code>.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> s = "babad"
<strong>Output:</strong> "bab"
<strong>Explanation:</strong> "aba" is also a valid answer.</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>1 &lt;= s.length &lt;= 1000</code></li>
    <li><code>s</code> consist of only digits and English letters (lower-case and upper-case).</li>
  </ul>
</div>',
2,
'longest-palindromic-substring',
'String, Dynamic Programming',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(26, 6, '{ "s": "babad", "expect": ["bab", "aba"] }', NULL, NULL, 0, SYSDATETIME()),
(27, 6, '{ "s": "cbbd", "expect": ["bb"] }', NULL, NULL, 0, SYSDATETIME()),
(28, 6, '{ "s": "a", "expect": ["a"] }', NULL, NULL, 0, SYSDATETIME()),
(29, 6, '{ "s": "ac", "expect": ["a", "c"] }', NULL, NULL, 1, SYSDATETIME()),
(30, 6, '{ "s": "abb", "expect": ["bb"] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(7,
'Zigzag Conversion',
'<div class="problem-content">
  <h1>Zigzag Conversion</h1>
  <p>Given a string s and a number of rows, write the code to convert the string in a zigzag pattern on the given number of rows, and then read it line by line.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> s = "PAYPALISHIRING", numRows = 3
<strong>Output:</strong> "PAHNAPLSIIGYIR"</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> s = "PAYPALISHIRING", numRows = 4
<strong>Output:</strong> "PINALSIGYAHRPI"</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>1 &lt;= s.length &lt;= 1000</code></li>
    <li><code>1 &lt;= numRows &lt;= 1000</code></li>
  </ul>
</div>',
2,
'zigzag-conversion',
'String',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(31, 7, '{ "s": "PAYPALISHIRING", "numRows": 3, "expect": "PAHNAPLSIIGYIR" }', NULL, NULL, 0, SYSDATETIME()),
(32, 7, '{ "s": "PAYPALISHIRING", "numRows": 4, "expect": "PINALSIGYAHRPI" }', NULL, NULL, 0, SYSDATETIME()),
(33, 7, '{ "s": "A", "numRows": 1, "expect": "A" }', NULL, NULL, 0, SYSDATETIME()),
(34, 7, '{ "s": "HELLO", "numRows": 2, "expect": "HLOEL" }', NULL, NULL, 1, SYSDATETIME()),
(35, 7, '{ "s": "ABCD", "numRows": 3, "expect": "ABDC" }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(8,
'Reverse Integer',
'<div class="problem-content">
  <h1>Reverse Integer</h1>
  <p>Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit integer range [-231, 231 - 1], then return 0.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> x = 123
<strong>Output:</strong> 321</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> x = -123
<strong>Output:</strong> -321</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> x = 120
<strong>Output:</strong> 21</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>-231 &lt;= x &lt;= 231 - 1</code></li>
  </ul>
</div>',
2,
'reverse-integer',
'Math',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(36, 8, '{ "x": 123, "expect": 321 }', NULL, NULL, 0, SYSDATETIME()),
(37, 8, '{ "x": -123, "expect": -321 }', NULL, NULL, 0, SYSDATETIME()),
(38, 8, '{ "x": 120, "expect": 21 }', NULL, NULL, 0, SYSDATETIME()),
(39, 8, '{ "x": 0, "expect": 0 }', NULL, NULL, 1, SYSDATETIME()),
(40, 8, '{ "x": 1534236469, "expect": 0 }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(9,
'String to Integer (myAtoi)',
'<div class="problem-content">
  <h1>String to Integer (myAtoi)</h1>
  <p>Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> s = "42"
<strong>Output:</strong> 42</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> s = "   -42"
<strong>Output:</strong> -42</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> s = "4193 with words"
<strong>Output:</strong> 4193</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>0 &lt;= s.length &lt;= 200</code></li>
    <li><code>s[i]</code> is one of the printable characters.</li>
  </ul>
</div>',
2,
'string-to-integer',
'String',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(41, 9, '{ "s": "42", "expect": 42 }', NULL, NULL, 0, SYSDATETIME()),
(42, 9, '{ "s": "   -42", "expect": -42 }', NULL, NULL, 0, SYSDATETIME()),
(43, 9, '{ "s": "4193 with words", "expect": 4193 }', NULL, NULL, 0, SYSDATETIME()),
(44, 9, '{ "s": "words and 987", "expect": 0 }', NULL, NULL, 1, SYSDATETIME()),
(45, 9, '{ "s": "-91283472332", "expect": -2147483648 }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(10,
'Palindrome Number',
'<div class="problem-content">
  <h1>Palindrome Number</h1>
  <p>Given an integer x, return true if x is a palindrome, and false otherwise.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> x = 121
<strong>Output:</strong> true</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> x = -121
<strong>Output:</strong> false</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> x = 10
<strong>Output:</strong> false</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>-2^31 &lt;= x &lt;= 2^31 - 1</code></li>
  </ul>
</div>',
1,
'palindrome-number',
'Math',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(46, 10, '{ "x": 121, "expect": true }', NULL, NULL, 0, SYSDATETIME()),
(47, 10, '{ "x": -121, "expect": false }', NULL, NULL, 0, SYSDATETIME()),
(48, 10, '{ "x": 10, "expect": false }', NULL, NULL, 0, SYSDATETIME()),
(49, 10, '{ "x": 12321, "expect": true }', NULL, NULL, 1, SYSDATETIME()),
(50, 10, '{ "x": 0, "expect": true }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(11,
'Regular Expression Matching',
'<div class="problem-content">
  <h1>Regular Expression Matching</h1>
  <p>Given an input string s and a pattern p, implement regular expression matching with support for "." and "*" where: "." Matches any single character. "*" Matches zero or more of the preceding element. The matching should cover the entire input string (not partial).</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> s = "aa", p = "a*"
<strong>Output:</strong> true</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> s = "mississippi", p = "mis*is*p*."
<strong>Output:</strong> false</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> s = "ab", p = ".*"
<strong>Output:</strong> true</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>1 &lt;= s.length &lt;= 20</code></li>
    <li><code>1 &lt;= p.length &lt;= 20</code></li>
  </ul>
</div>',
3,
'regular-expression-matching',
'String, Dynamic Programming',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(51, 11, '{ "s": "aa", "p": "a", "expect": false }', NULL, NULL, 0, SYSDATETIME()),
(52, 11, '{ "s": "aa", "p": "a*", "expect": true }', NULL, NULL, 0, SYSDATETIME()),
(53, 11, '{ "s": "ab", "p": ".*", "expect": true }', NULL, NULL, 0, SYSDATETIME()),
(54, 11, '{ "s": "aab", "p": "c*a*b", "expect": true }', NULL, NULL, 1, SYSDATETIME()),
(55, 11, '{ "s": "mississippi", "p": "mis*is*p*.", "expect": false }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(12,
'Container With Most Water',
'<div class="problem-content">
  <h1>Container With Most Water</h1>
  <p>You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]). Find two lines that together with the x-axis form a container, such that the container contains the most water. Return the maximum amount of water a container can store. Notice that you may not slant the container.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> height = [1,8,6,2,5,4,8,3,7]
<strong>Output:</strong> 49</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> height = [1,1]
<strong>Output:</strong> 1</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>2 &lt;= height.length &lt;= 10^5</code></li>
    <li><code>0 &lt;= height[i] &lt;= 10^4</code></li>
  </ul>
</div>',
2,
'container-with-most-water',
'Two Pointers',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(56, 12, '{ "height": [1, 1], "expect": 1 }', NULL, NULL, 0, SYSDATETIME()),
(57, 12, '{ "height": [4, 3, 2, 1, 4], "expect": 16 }', NULL, NULL, 0, SYSDATETIME()),
(58, 12, '{ "height": [1, 2, 1], "expect": 2 }', NULL, NULL, 0, SYSDATETIME()),
(59, 12, '{ "height": [1, 8, 6, 2, 5, 4, 8, 3, 7], "expect": 49 }', NULL, NULL, 1, SYSDATETIME()),
(60, 12, '{ "height": [1, 3, 2, 5, 25, 24, 5], "expect": 24 }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(13,
'Integer to Roman',
'<div class="problem-content">
  <h1>Integer to Roman</h1>
  <p>Given an integer, convert it to a Roman numeral. The conversion is based on the Roman numeral system with specific rules for subtractive notation and placement of symbols.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> num = 3
<strong>Output:</strong> "III"</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> num = 58
<strong>Output:</strong> "LVIII"</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> num = 1994
<strong>Output:</strong> "MCMXCIV"</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>1 &lt;= num &lt;= 3999</code></li>
  </ul>
</div>',
2,
'integer-to-roman',
'Math, String',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(61, 13, '{ "num": 3, "expect": "III" }', NULL, NULL, 0, SYSDATETIME()),
(62, 13, '{ "num": 4, "expect": "IV" }', NULL, NULL, 0, SYSDATETIME()),
(63, 13, '{ "num": 9, "expect": "IX" }', NULL, NULL, 0, SYSDATETIME()),
(64, 13, '{ "num": 58, "expect": "LVIII" }', NULL, NULL, 1, SYSDATETIME()),
(65, 13, '{ "num": 1994, "expect": "MCMXCIV" }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(14,
'Roman to Integer',
'<div class="problem-content">
  <h1>Roman to Integer</h1>
  <p>Given a Roman numeral, convert it to an integer. Roman numerals are formed using the following symbols: I, V, X, L, C, D, M. Some numerals use subtraction notation (e.g., IV for 4, IX for 9). The valid range for Roman numerals in this problem is [1, 3999].</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> s = "III"
<strong>Output:</strong> 3</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> s = "LVIII"
<strong>Output:</strong> 58</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> s = "MCMXCIV"
<strong>Output:</strong> 1994</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>1 &lt;= s.length &lt;= 15</code></li>
    <li><code>s</code> consists of valid Roman numeral characters.</li>
  </ul>
</div>',
2,
'roman-to-integer',
'Math, String',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(66, 14, '{ "roman": "III", "expect": 3 }', NULL, NULL, 0, SYSDATETIME()),
(67, 14, '{ "roman": "IV", "expect": 4 }', NULL, NULL, 0, SYSDATETIME()),
(68, 14, '{ "roman": "IX", "expect": 9 }', NULL, NULL, 0, SYSDATETIME()),
(69, 14, '{ "roman": "LVIII", "expect": 58 }', NULL, NULL, 1, SYSDATETIME()),
(70, 14, '{ "roman": "MCMXCIV", "expect": 1994 }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(15,
'Longest Common Prefix',
'<div class="problem-content">
  <h1>Longest Common Prefix</h1>
  <p>Write a function to find the longest common prefix string amongst an array of strings. If there is no common prefix, return an empty string.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> strs = ["flower","flow","flight"]
<strong>Output:</strong> "fl"</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> strs = ["dog","racecar","car"]
<strong>Output:</strong> ""</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>0 &lt;= strs.length &lt;= 200</code></li>
    <li><code>0 &lt;= strs[i].length &lt;= 200</code></li>
    <li><code>strs[i]</code> consists of only lowercase English letters.</li>
  </ul>
</div>',
2,
'longest-common-prefix',
'String',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(71, 15, '{ "strs": ["flower", "flow", "flight"], "expect": "fl" }', NULL, NULL, 0, SYSDATETIME()),
(72, 15, '{ "strs": ["dog", "racecar", "car"], "expect": "" }', NULL, NULL, 0, SYSDATETIME()),
(73, 15, '{ "strs": ["interspecies", "interstellar", "interstate"], "expect": "inters" }', NULL, NULL, 0, SYSDATETIME()),
(74, 15, '{ "strs": ["throne", "throne"], "expect": "throne" }', NULL, NULL, 1, SYSDATETIME()),
(75, 15, '{ "strs": ["a"], "expect": "a" }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(16,
'3Sum',
'<div class="problem-content">
  <h1>3Sum</h1>
  <p>Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0. Notice that the solution set must not contain duplicate triplets.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> nums = [-1,0,1,2,-1,-4]
<strong>Output:</strong> [[-1,-1,2],[-1,0,1]]</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> nums = []
<strong>Output:</strong> []</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> nums = [0]
<strong>Output:</strong> []</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>0 &lt;= nums.length &lt;= 3000</code></li>
    <li><code>-10^5 &lt;= nums[i] &lt;= 10^5</code></li>
  </ul>
</div>',
2,
'3-sum',
'Two Pointers, Array',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(76, 16, '{ "nums": [-1, 0, 1, 2, -1, -4], "expect": [[-1, -1, 2], [-1, 0, 1]] }', NULL, NULL, 0, SYSDATETIME()),
(77, 16, '{ "nums": [0, 0, 0, 0], "expect": [[0, 0, 0]] }', NULL, NULL, 0, SYSDATETIME()),
(78, 16, '{ "nums": [-1, 0, 1], "expect": [[-1, 0, 1]] }', NULL, NULL, 0, SYSDATETIME()),
(79, 16, '{ "nums": [1, 2, -2, -1], "expect": [] }', NULL, NULL, 1, SYSDATETIME()),
(80, 16, '{ "nums": [-1, -1, 2, 2], "expect": [[-1, -1, 2]] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(17,
'3Sum Closest',
'<div class="problem-content">
  <h1>3Sum Closest</h1>
  <p>Given an integer array nums of length n and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> nums = [-1,2,1,-4], target = 1
<strong>Output:</strong> 2</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> nums = [0,0,0], target = 1
<strong>Output:</strong> 0</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>3 &lt;= nums.length &lt;= 1000</code></li>
    <li><code>-10^3 &lt;= nums[i] &lt;= 10^3</code></li>
    <li><code>-10^3 &lt;= target &lt;= 10^3</code></li>
  </ul>
</div>',
2,
'3-sum-closest',
'Two Pointers, Array',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(81, 17, '{ "nums": [-1, 2, 1, -4], "target": 1, "expect": 2 }', NULL, NULL, 0, SYSDATETIME()),
(82, 17, '{ "nums": [0, 0, 0], "target": 1, "expect": 0 }', NULL, NULL, 0, SYSDATETIME()),
(83, 17, '{ "nums": [1, 1, 1], "target": 3, "expect": 3 }', NULL, NULL, 0, SYSDATETIME()),
(84, 17, '{ "nums": [-1, 0, 1, 1], "target": 0, "expect": 0 }', NULL, NULL, 1, SYSDATETIME()),
(85, 17, '{ "nums": [1, 2, 3], "target": 6, "expect": 6 }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(18,
'Letter Combinations of a Phone Number',
'<div class="problem-content">
  <h1>Letter Combinations of a Phone Number</h1>
  <p>Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> digits = "23"
<strong>Output:</strong> ["ad","ae","af","bd","be","bf","cd","ce","cf"]</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> digits = ""
<strong>Output:</strong> []</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> digits = "2"
<strong>Output:</strong> ["a","b","c"]</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>0 &lt;= digits.length &lt;= 4</code></li>
    <li><code>digits[i]</code> is a digit from "2" to "9".</li>
  </ul>
</div>',
2,
'letter-combinations-of-a-phone-number',
'Backtracking',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(86, 18, '{ "digits": "23", "expect": ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"] }', NULL, NULL, 0, SYSDATETIME()),
(87, 18, '{ "digits": "2", "expect": ["a", "b", "c"] }', NULL, NULL, 0, SYSDATETIME()),
(88, 18, '{ "digits": "78", "expect": ["pq", "pr", "ps", "pq", "pr", "ps", "qt", "qu", "qv", "qt", "qu", "qv"] }', NULL, NULL, 0, SYSDATETIME()),
(89, 18, '{ "digits": "", "expect": [] }', NULL, NULL, 1, SYSDATETIME()),
(90, 18, '{ "digits": "7", "expect": ["p", "q", "r", "s"] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(19,
'4Sum',
'<div class="problem-content">
  <h1>4Sum</h1>
  <p>Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that: 0 <= a, b, c, d < n, a, b, c, and d are distinct, nums[a] + nums[b] + nums[c] + nums[d] == target. You may return the answer in any order.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> nums = [1,0,-1,0,-2,2], target = 0
<strong>Output:</strong> [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> nums = [], target = 0
<strong>Output:</strong> []</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>0 &lt;= nums.length &lt;= 200</code></li>
    <li><code>-10^9 &lt;= nums[i] &lt;= 10^9</code></li>
    <li><code>-10^9 &lt;= target &lt;= 10^9</code></li>
  </ul>
</div>',
3,
'4-sum',
'Two Pointers, Array',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(91, 19, '{ "nums": [1, 0, -1, 0, -2, 2], "target": 0, "expect": [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]] }', NULL, NULL, 0, SYSDATETIME()),
(92, 19, '{ "nums": [2, 2, 2, 2, 2], "target": 8, "expect": [[2, 2, 2, 2]] }', NULL, NULL, 0, SYSDATETIME()),
(93, 19, '{ "nums": [1, 1, 1, 1], "target": 4, "expect": [[1, 1, 1, 1]] }', NULL, NULL, 0, SYSDATETIME()),
(94, 19, '{ "nums": [1, 2, 3, 4, 5, 6, 7, 8, 9], "target": 10, "expect": [[1, 2, 3, 4], [1, 2, 2, 5], [1, 3, 2, 4]] }', NULL, NULL, 1, SYSDATETIME()),
(95, 19, '{ "nums": [1, 0, -1, 0, -2, 2], "target": 8, "expect": [] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(20,
'Remove Nth Node From End of List',
'<div class="problem-content">
  <h1>Remove Nth Node From End of List</h1>
  <p>Given the head of a linked list, remove the nth node from the end of the list and return its head.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> head = [1,2,3,4,5], n = 2
<strong>Output:</strong> [1,2,3,5]</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> head = [1], n = 1
<strong>Output:</strong> []</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>The number of nodes in the list is <code>sz</code>.</code></li>
    <li><code>1 &lt;= sz &lt;= 30</code></li>
    <li><code>0 &lt;= Node.val &lt;= 100</code></li>
    <li><code>1 &lt;= n &lt;= sz</code></li>
  </ul>
</div>',
1,
'remove-nth-node-from-end-of-list',
'Linked List, Two Pointers',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(96, 20, '{ "head": [1, 2, 3, 4, 5], "n": 2, "expect": [1, 2, 3, 5] }', NULL, NULL, 0, SYSDATETIME()),
(97, 20, '{ "head": [1], "n": 1, "expect": [] }', NULL, NULL, 0, SYSDATETIME()),
(98, 20, '{ "head": [1, 2], "n": 1, "expect": [1] }', NULL, NULL, 0, SYSDATETIME()),
(99, 20, '{ "head": [1, 2, 3, 4, 5], "n": 1, "expect": [1, 2, 3, 4] }', NULL, NULL, 1, SYSDATETIME()),
(100, 20, '{ "head": [1, 2, 3, 4, 5], "n": 5, "expect": [2, 3, 4, 5] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(21,
'Merge Two Sorted Lists',
'<div class="problem-content">
  <h1>Merge Two Sorted Lists</h1>
  <p>You are given the heads of two sorted linked lists list1 and list2. Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists. Return the head of the merged linked list.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> list1 = [1,2,4], list2 = [1,3,4]
<strong>Output:</strong> [1,1,2,3,4,4]</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> list1 = [], list2 = [0]
<strong>Output:</strong> [0]</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>0 <= list1.length, list2.length <= 10^4</code></li>
    <li><code>-100 <= list1[i], list2[i] <= 100</code></li>
  </ul>
</div>',
3,
'merge-two-sorted-lists',
'Linked List',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(101, 21, '{ "list1": [1, 2, 4], "list2": [1, 3, 4], "expect": [1, 1, 2, 3, 4, 4] }', NULL, NULL, 0, SYSDATETIME()),
(102, 21, '{ "list1": [], "list2": [0], "expect": [0] }', NULL, NULL, 0, SYSDATETIME()),
(103, 21, '{ "list1": [1], "list2": [], "expect": [1] }', NULL, NULL, 0, SYSDATETIME()),
(104, 21, '{ "list1": [], "list2": [], "expect": [] }', NULL, NULL, 1, SYSDATETIME()),
(105, 21, '{ "list1": [1, 2, 3, 5], "list2": [4, 6, 7], "expect": [1, 2, 3, 4, 5, 6, 7] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(22,
'Generate Parentheses',
'<div class="problem-content">
  <h1>Generate Parentheses</h1>
  <p>Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> n = 3
<strong>Output:</strong> ["((()))","(()())","(())()","()(())","()()()"]</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> n = 1
<strong>Output:</strong> ["()"]</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>1 <= n <= 8</code></li>
  </ul>
</div>',
3,
'generate-parentheses',
'Backtracking',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(106, 22, '{ "n": 3, "expect": ["((()))", "(()())", "(())()", "()(())", "()()()"] }', NULL, NULL, 0, SYSDATETIME()),
(107, 22, '{ "n": 1, "expect": ["()"] }', NULL, NULL, 0, SYSDATETIME()),
(108, 22, '{ "n": 2, "expect": ["(())", "()()"] }', NULL, NULL, 0, SYSDATETIME()),
(109, 22, '{ "n": 4, "expect": ["(((())))", "((()()))", "((())())", "((()))()", "(()(()))", "(()()())", "(()())()", "(())(())", "(())()()", "()((()))", "()(()())", "()(())()", "()()(())", "()()()()"] }', NULL, NULL, 1, SYSDATETIME()),
(110, 22, '{ "n": 0, "expect": [] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(23,
'Reverse Linked List',
'<div class="problem-content">
  <h1>Reverse Linked List</h1>
  <p>Given the head of a singly linked list, reverse the list and return its head.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> head = [1,2,3,4,5]
<strong>Output:</strong> [5,4,3,2,1]</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> head = [1,2]
<strong>Output:</strong> [2,1]</code></pre>
  <h3 class="section-title">Example 3:</h3>
  <pre><code><strong>Input:</strong> head = []
<strong>Output:</strong> []</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>0 <= ListNode.length <= 5000</code></li>
    <li><code>-5000 <= ListNode.val <= 5000</code></li>
  </ul>
</div>',
2,
'reverse-linked-list',
'Linked List',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(111, 23, '{ "lists": [[1,4,5],[1,3,4],[2,6]], "expect": [1,1,2,3,4,4,5,6] }', NULL, NULL, 0, SYSDATETIME()),
(112, 23, '{ "lists": [[],[],[]], "expect": [] }', NULL, NULL, 0, SYSDATETIME()),
(113, 23, '{ "lists": [[1,2,3],[],[4,5,6]], "expect": [1,2,3,4,5,6] }', NULL, NULL, 0, SYSDATETIME()),
(114, 23, '{ "lists": [[-10,-5,0],[-5,0,5],[-10,10]], "expect": [-10,-10,-5,-5,0,0,5,10,10] }', NULL, NULL, 1, SYSDATETIME()),
(115, 23, '{ "lists": [[2,5,7],[],[3,6]], "expect": [2,3,5,6,7] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO problem(id, title, description, difficulty, key_path, category, video_id, number_testcase, create_time)
VALUES
(24,
'Merge Intervals',
'<div class="problem-content">
  <h1>Merge Intervals</h1>
  <p>Given an array of intervals where <code>intervals[i] = [start_i, end_i]</code>, merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.</p>
  <h3 class="section-title">Example 1:</h3>
  <pre><code><strong>Input:</strong> intervals = [[1,3],[2,6],[8,10],[15,18]]
<strong>Output:</strong> [[1,6],[8,10],[15,18]]</code></pre>
  <h3 class="section-title">Example 2:</h3>
  <pre><code><strong>Input:</strong> intervals = [[1,4],[4,5]]
<strong>Output:</strong> [[1,5]]</code></pre>
  <h3 class="section-title">Constraints:</h3>
  <ul>
    <li><code>1 <= intervals.length <= 10^4</code></li>
    <li><code>intervals[i].length == 2</code></li>
    <li><code>0 <= start_i <= end_i <= 10^4</code></li>
  </ul>
</div>',
2,
'merge-intervals',
'Sort, Array',
'',
5,
SYSDATETIME());

INSERT INTO testcase(id, problem_id, testcase_json, illustration, explanation, is_hidden, create_time)
VALUES
(116, 24, '{ "head": [1,2,3,4,5], "k": 2, "expect": [2,1,4,3,5] }', NULL, NULL, 0, SYSDATETIME()),
(117, 24, '{ "head": [1,2,3,4,5], "k": 3, "expect": [3,2,1,4,5] }', NULL, NULL, 0, SYSDATETIME()),
(118, 24, '{ "head": [1,2,3,4,5], "k": 1, "expect": [1,2,3,4,5] }', NULL, NULL, 0, SYSDATETIME()),
(119, 24, '{ "head": [1], "k": 1, "expect": [1] }', NULL, NULL, 1, SYSDATETIME()),
(120, 24, '{ "head": [], "k": 3, "expect": [] }', NULL, NULL, 1, SYSDATETIME());

INSERT INTO exam(id, title, description, start_time, end_time, create_time)
VALUES
(1,
'Midterm exam for general computer science',
'The test has a score coefficient of 0.2.',
'2024-11-29 10:00:00',
'2024-11-29 11:00:00',
SYSDATETIME());

INSERT INTO exam(id, title, description, start_time, end_time, create_time)
VALUES
(2,
'Final exam for data structures and algorithms',
'The test has a score coefficient of 0.6.',
'2024-12-10 09:00:00',
'2024-12-10 10:00:00',
SYSDATETIME());

INSERT INTO exam(id, title, description, start_time, end_time, create_time)
VALUES
(3,
'Periodic exam for c++ programming',
'The test has a score coefficient of 0.1.',
'2024-12-11 07:00:00',
'2024-12-11 08:00:00',
SYSDATETIME());

IF OBJECT_ID('problem_function_signature', 'U') IS NOT NULL
  DROP TABLE problem_function_signature;
GO

CREATE TABLE problem_function_signature (
  id                bigint IDENTITY(1,1) PRIMARY KEY,
  problem_id        bigint NOT NULL,
  language          nvarchar(50) NOT NULL,
  function_signature nvarchar(255) NOT NULL,
  tenant_id         nvarchar(20) DEFAULT ('000000') NULL,
  create_by         bigint NULL,
  create_time       datetime2(7) NULL,
  update_by         bigint NULL,
  update_time       datetime2(7) NULL,
  remark            nvarchar(255) NULL
);
GO

-- Chèn function signatures cho các bài toán
INSERT INTO problem_function_signature (problem_id, language, function_signature)
VALUES
  -- Two Sum
  (1, 'JavaScript', 'twoSum = function(nums, target)'),
  (1, 'Java', 'int[] twoSum(int[] nums, int target)'),
  (1, 'PHP', 'twoSum($nums, $target)'),
  (1, 'C', 'int* twoSum(int* nums, int numsSize, int target, int* returnSize)'),
  (1, 'C++', 'vector<int> twoSum(vector<int>& nums, int target)'),
  (1, 'C#', 'int[] TwoSum(int[] nums, int target)'),
  (1, 'Python', 'def twoSum(self, nums: List[int], target: int) -> List[int]:'),
  (1, 'Go', 'func twoSum(nums []int, target int) []int'),

  -- Reverse Linked List
  (2, 'JavaScript', 'reverseList = function(head)'),
  (2, 'Java', 'ListNode reverseList(ListNode head)'),
  (2, 'PHP', 'reverseList($head)'),
  (2, 'C', 'struct ListNode* reverseList(struct ListNode* head)'),
  (2, 'C++', 'ListNode* reverseList(ListNode* head)'),
  (2, 'C#', 'ListNode ReverseList(ListNode head)'),
  (2, 'Python', 'def reverseList(self, head: Optional[ListNode]) -> Optional[ListNode]:'),
  (2, 'Go', 'func reverseList(head *ListNode) *ListNode'),

  -- Jump Game
  (3, 'JavaScript', 'canJump = function(nums)'),
  (3, 'Java', 'boolean canJump(int[] nums)'),
  (3, 'PHP', 'canJump($nums)'),
  (3, 'C', 'bool canJump(int* nums, int numsSize)'),
  (3, 'C++', 'bool canJump(vector<int>& nums)'),
  (3, 'C#', 'bool CanJump(int[] nums)'),
  (3, 'Python', 'def canJump(self, nums: List[int]) -> bool:'),
  (3, 'Go', 'func canJump(nums []int) bool'),

  -- Valid Parentheses
  (4, 'JavaScript', 'isValid = function(s)'),
  (4, 'Java', 'boolean isValid(String s)'),
  (4, 'PHP', 'isValid($s)'),
  (4, 'C', 'bool isValid(char* s)'),
  (4, 'C++', 'bool isValid(string s)'),
  (4, 'C#', 'bool IsValid(string s)'),
  (4, 'Python', 'def isValid(self, s: str) -> bool:'),
  (4, 'Go', 'func isValid(s string) bool'),

  -- Search a 2D Matrix
  (5, 'JavaScript', 'searchMatrix = function(matrix, target)'),
  (5, 'Java', 'boolean searchMatrix(int[][] matrix, int target)'),
  (5, 'PHP', 'searchMatrix($matrix, $target)'),
  (5, 'C', 'bool searchMatrix(int** matrix, int matrixSize, int* matrixColSize, int target)'),
  (5, 'C++', 'bool searchMatrix(vector<vector<int>>& matrix, int target)'),
  (5, 'C#', 'bool SearchMatrix(int[][] matrix, int target)'),
  (5, 'Python', 'def searchMatrix(self, matrix: List[List[int]], target: int) -> bool:'),
  (5, 'Go', 'func searchMatrix(matrix [][]int, target int) bool'),

  -- Longest Palindromic Substring
  (6, 'JavaScript', 'longestPalindrome = function(s)'),
  (6, 'Java', 'String longestPalindrome(String s)'),
  (6, 'PHP', 'longestPalindrome($s)'),
  (6, 'C', 'char* longestPalindrome(char* s)'),
  (6, 'C++', 'string longestPalindrome(string s)'),
  (6, 'C#', 'string LongestPalindrome(string s)'),
  (6, 'Python', 'def longestPalindrome(self, s: str) -> str:'),
  (6, 'Go', 'func longestPalindrome(s string) string'),

  -- Zigzag Conversion
  (7, 'JavaScript', 'convert = function(s, numRows)'),
  (7, 'Java', 'String convert(String s, int numRows)'),
  (7, 'PHP', 'convert($s, $numRows)'),
  (7, 'C', 'char* convert(char* s, int numRows)'),
  (7, 'C++', 'string convert(string s, int numRows)'),
  (7, 'C#', 'string Convert(string s, int numRows)'),
  (7, 'Python', 'def convert(self, s: str, numRows: int) -> str:'),
  (7, 'Go', 'func convert(s string, numRows int) string'),

  -- Reverse Integer
  (8, 'JavaScript', 'reverse = function(x)'),
  (8, 'Java', 'int reverse(int x)'),
  (8, 'PHP', 'reverse($x)'),
  (8, 'C', 'int reverse(int x)'),
  (8, 'C++', 'int reverse(int x)'),
  (8, 'C#', 'int Reverse(int x)'),
  (8, 'Python', 'def reverse(self, x: int) -> int:'),
  (8, 'Go', 'func reverse(x int) int'),

-- String to Integer (myAtoi)
  (9, 'JavaScript', 'myAtoi = function(s)'),
  (9, 'Java', 'int myAtoi(String s)'),
  (9, 'PHP', 'myAtoi($s)'),
  (9, 'C', 'int myAtoi(char* s)'),
  (9, 'C++', 'int myAtoi(string s)'),
  (9, 'C#', 'int MyAtoi(string s)'),
  (9, 'Python', 'def myAtoi(self, s: str) -> int:'),
  (9, 'Go', 'func myAtoi(s string) int'),

  -- Palindrome Number
  (10, 'JavaScript', 'isPalindrome = function(x)'),
  (10, 'Java', 'boolean isPalindrome(int x)'),
  (10, 'PHP', 'isPalindrome($x)'),
  (10, 'C', 'bool isPalindrome(int x)'),
  (10, 'C++', 'bool isPalindrome(int x)'),
  (10, 'C#', 'bool IsPalindrome(int x)'),
  (10, 'Python', 'def isPalindrome(self, x: int) -> bool:'),
  (10, 'Go', 'func isPalindrome(x int) bool'),

  -- Regular Expression Matching
  (11, 'JavaScript', 'isMatch = function(s, p)'),
  (11, 'Java', 'boolean isMatch(String s, String p)'),
  (11, 'PHP', 'isMatch($s, $p)'),
  (11, 'C', 'bool isMatch(char* s, char* p)'),
  (11, 'C++', 'bool isMatch(string s, string p)'),
  (11, 'C#', 'bool IsMatch(string s, string p)'),
  (11, 'Python', 'def isMatch(self, s: str, p: str) -> bool:'),
  (11, 'Go', 'func isMatch(s string, p string) bool'),

  -- Container With Most Water
  (12, 'JavaScript', 'maxArea = function(height)'),
  (12, 'Java', 'int maxArea(int[] height)'),
  (12, 'PHP', 'maxArea($height)'),
  (12, 'C', 'int maxArea(int* height, int heightSize)'),
  (12, 'C++', 'int maxArea(vector<int>& height)'),
  (12, 'C#', 'int MaxArea(int[] height)'),
  (12, 'Python', 'def maxArea(self, height: List[int]) -> int:'),
  (12, 'Go', 'func maxArea(height []int) int'),

  -- Integer to Roman
  (13, 'JavaScript', 'intToRoman = function(num)'),
  (13, 'Java', 'String intToRoman(int num)'),
  (13, 'PHP', 'intToRoman($num)'),
  (13, 'C', 'char* intToRoman(int num)'),
  (13, 'C++', 'string intToRoman(int num)'),
  (13, 'C#', 'string IntToRoman(int num)'),
  (13, 'Python', 'def intToRoman(self, num: int) -> str:'),
  (13, 'Go', 'func intToRoman(num int) string'),

  -- Roman to Integer
  (14, 'JavaScript', 'romanToInt = function(s)'),
  (14, 'Java', 'int romanToInt(String s)'),
  (14, 'PHP', 'romanToInt($s)'),
  (14, 'C', 'int romanToInt(char* s)'),
  (14, 'C++', 'int romanToInt(string s)'),
  (14, 'C#', 'int RomanToInt(string s)'),
  (14, 'Python', 'def romanToInt(self, s: str) -> int:'),
  (14, 'Go', 'func romanToInt(s string) int'),

  -- Longest Common Prefix
  (15, 'JavaScript', 'longestCommonPrefix = function(strs)'),
  (15, 'Java', 'String longestCommonPrefix(String[] strs)'),
  (15, 'PHP', 'longestCommonPrefix($strs)'),
  (15, 'C', 'char* longestCommonPrefix(char** strs, int strsSize)'),
  (15, 'C++', 'string longestCommonPrefix(vector<string>& strs)'),
  (15, 'C#', 'string LongestCommonPrefix(string[] strs)'),
  (15, 'Python', 'def longestCommonPrefix(self, strs: List[str]) -> str:'),
  (15, 'Go', 'func longestCommonPrefix(strs []string) string'),

-- 3Sum
  (16, 'JavaScript', 'threeSum = function(nums)'),
  (16, 'Java', 'List<List<Integer>> threeSum(int[] nums)'),
  (16, 'PHP', 'threeSum($nums)'),
  (16, 'C', 'int** threeSum(int* nums, int numsSize, int* returnSize, int** returnColumnSizes)'),
  (16, 'C++', 'vector<vector<int>> threeSum(vector<int>& nums)'),
  (16, 'C#', 'IList<IList<int>> ThreeSum(int[] nums)'),
  (16, 'Python', 'def threeSum(self, nums: List[int]) -> List[List[int]]:'),
  (16, 'Go', 'func threeSum(nums []int) [][]int'),

  -- 3Sum Closest
  (17, 'JavaScript', 'threeSumClosest = function(nums, target)'),
  (17, 'Java', 'int threeSumClosest(int[] nums, int target)'),
  (17, 'PHP', 'threeSumClosest($nums, $target)'),
  (17, 'C', 'int threeSumClosest(int* nums, int numsSize, int target)'),
  (17, 'C++', 'int threeSumClosest(vector<int>& nums, int target)'),
  (17, 'C#', 'int ThreeSumClosest(int[] nums, int target)'),
  (17, 'Python', 'def threeSumClosest(self, nums: List[int], target: int) -> int:'),
  (17, 'Go', 'func threeSumClosest(nums []int, target int) int'),

  -- Letter Combinations of a Phone Number
  (18, 'JavaScript', 'letterCombinations = function(digits)'),
  (18, 'Java', 'List<String> letterCombinations(String digits)'),
  (18, 'PHP', 'letterCombinations($digits)'),
  (18, 'C', 'char** letterCombinations(char* digits, int* returnSize)'),
  (18, 'C++', 'vector<string> letterCombinations(string digits)'),
  (18, 'C#', 'IList<string> LetterCombinations(string digits)'),
  (18, 'Python', 'def letterCombinations(self, digits: str) -> List[str]:'),
  (18, 'Go', 'func letterCombinations(digits string) []string'),

  -- 4Sum
  (19, 'JavaScript', 'fourSum = function(nums, target)'),
  (19, 'Java', 'List<List<Integer>> fourSum(int[] nums, int target)'),
  (19, 'PHP', 'fourSum($nums, $target)'),
  (19, 'C', 'int** fourSum(int* nums, int numsSize, int target, int* returnSize, int** returnColumnSizes)'),
  (19, 'C++', 'vector<vector<int>> fourSum(vector<int>& nums, int target)'),
  (19, 'C#', 'IList<IList<int>> FourSum(int[] nums, int target)'),
  (19, 'Python', 'def fourSum(self, nums: List[int], target: int) -> List[List[int]]:'),
  (19, 'Go', 'func fourSum(nums []int, target int) [][]int'),

  -- Remove Nth Node From End of List
  (20, 'JavaScript', 'removeNthFromEnd = function(head, n)'),
  (20, 'Java', 'ListNode removeNthFromEnd(ListNode head, int n)'),
  (20, 'PHP', 'removeNthFromEnd($head, $n)'),
  (20, 'C', 'struct ListNode* removeNthFromEnd(struct ListNode* head, int n)'),
  (20, 'C++', 'ListNode* removeNthFromEnd(ListNode* head, int n)'),
  (20, 'C#', 'ListNode RemoveNthFromEnd(ListNode head, int n)'),
  (20, 'Python', 'def removeNthFromEnd(self, head: Optional[ListNode], n: int) -> Optional[ListNode]:'),
  (20, 'Go', 'func removeNthFromEnd(head *ListNode, n int) *ListNode'),

  -- Merge Two Sorted Lists
  (21, 'JavaScript', 'mergeTwoLists = function(list1, list2)'),
  (21, 'Java', 'ListNode mergeTwoLists(ListNode list1, ListNode list2)'),
  (21, 'PHP', 'mergeTwoLists($list1, $list2)'),
  (21, 'C', 'struct ListNode* mergeTwoLists(struct ListNode* list1, struct ListNode* list2)'),
  (21, 'C++', 'ListNode* mergeTwoLists(ListNode* list1, ListNode* list2)'),
  (21, 'C#', 'ListNode MergeTwoLists(ListNode list1, ListNode list2)'),
  (21, 'Python', 'def mergeTwoLists(self, list1: Optional[ListNode], list2: Optional[ListNode]) -> Optional[ListNode]:'),
  (21, 'Go', 'func mergeTwoLists(list1 *ListNode, list2 *ListNode) *ListNode'),

  -- Generate Parentheses
  (22, 'JavaScript', 'generateParenthesis = function(n)'),
  (22, 'Java', 'List<String> generateParenthesis(int n)'),
  (22, 'PHP', 'generateParenthesis($n)'),
  (22, 'C', 'char** generateParenthesis(int n, int* returnSize)'),
  (22, 'C++', 'vector<string> generateParenthesis(int n)'),
  (22, 'C#', 'IList<string> GenerateParenthesis(int n)'),
  (22, 'Python', 'def generateParenthesis(self, n: int) -> List[str]:'),
  (22, 'Go', 'func generateParenthesis(n int) []string'),

  -- Reverse Linked List
  (23, 'JavaScript', 'reverseList = function(head)'),
  (23, 'Java', 'ListNode reverseList(ListNode head)'),
  (23, 'PHP', 'reverseList($head)'),
  (23, 'C', 'struct ListNode* reverseList(struct ListNode* head)'),
  (23, 'C++', 'ListNode* reverseList(ListNode* head)'),
  (23, 'C#', 'ListNode ReverseList(ListNode head)'),
  (23, 'Python', 'def reverseList(self, head: Optional[ListNode]) -> Optional[ListNode]:'),
  (23, 'Go', 'func reverseList(head *ListNode) *ListNode'),

  -- Merge Intervals
  (24, 'JavaScript', 'merge = function(intervals)'),
  (24, 'Java', 'int[][] merge(int[][] intervals)'),
  (24, 'PHP', 'merge($intervals)'),
  (24, 'C', 'int** merge(int** intervals, int intervalsSize, int* intervalsColSize, int* returnSize, int** returnColumnSizes)'),
  (24, 'C++', 'vector<vector<int>> merge(vector<vector<int>>& intervals)'),
  (24, 'C#', 'int[][] Merge(int[][] intervals)'),
  (24, 'Python', 'def merge(self, intervals: List[List[int]]) -> List[List[int]]:'),
  (24, 'Go', 'func merge(intervals [][]int) [][]int');
