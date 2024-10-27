	-- Create the database
CREATE DATABASE NFTMark;
GO

-- Use the newly created database
USE NFTMark;
GO

-- Create the Accounts table
CREATE TABLE Accounts (
    AccountID INT PRIMARY KEY IDENTITY(1,1),
    Username NVARCHAR(50) NOT NULL UNIQUE,
    Password NVARCHAR(255) NOT NULL
);
GO

-- Create the Customer table
CREATE TABLE Customer (
    AccountID INT PRIMARY KEY,
    Fullname NVARCHAR(100),
    Email NVARCHAR(100) UNIQUE,
    Photo VARBINARY(MAX),
    Phone NVARCHAR(15),
    Walletkey NVARCHAR(255),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
);
GO

-- Create the Roles table
CREATE TABLE Roles (
    RoleID INT PRIMARY KEY IDENTITY(1,1),
    Rolename NVARCHAR(50) NOT NULL UNIQUE
);
GO

-- Create the Authorities table
CREATE TABLE Authorities (
    AccountID INT,
    RoleID INT,
    PRIMARY KEY (AccountID, RoleID),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID),
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);
GO

-- Create the ImageNFT table
CREATE TABLE ImageNFT (
    NftID INT PRIMARY KEY IDENTITY(1,1),
    Name NVARCHAR(100) NOT NULL,
    Price DECIMAL(18,2) NOT NULL,
    Image VARBINARY(MAX),
    Username NVARCHAR(50),
    Quantity INT NOT NULL,
    FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

-- Create the Comment table
CREATE TABLE Comment (
    CommentID INT PRIMARY KEY IDENTITY(1,1),
    NftID INT,
    Description NVARCHAR(255),
    Createdate DATETIME DEFAULT GETDATE(),
    Username NVARCHAR(50),
    FOREIGN KEY (NftID) REFERENCES ImageNFT(NftID),
    FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

-- Create the Transactions table
CREATE TABLE Transactions (
    TransactionsID INT PRIMARY KEY IDENTITY(1,1),
    Username NVARCHAR(50),
    TransactionsDate DATETIME DEFAULT GETDATE(),
    Transactionsdetails NVARCHAR(255),
    FOREIGN KEY (Username) REFERENCES Accounts(Username)
);
GO

-- Create the TransactionsDetail table
CREATE TABLE TransactionsDetail (
    TransactionsDetailID INT PRIMARY KEY IDENTITY(1,1),
    TransactionsID INT,
    Transactionsdetails NVARCHAR(255),
    Image VARBINARY(MAX),
    Price DECIMAL(18,2),
    FOREIGN KEY (TransactionsID) REFERENCES Transactions(TransactionsID)
);
GO
--Dùng lệnh này để thêm cột

alter table [NFTMark].[dbo].[accounts]
add [key_wallet] nvarchar(255);

alter table [NFTMark].[dbo].[imagenft]
add [key_wallet] nvarchar(255);

-- Insert data into Accounts
INSERT INTO accounts (username, password) VALUES
('user1', '123'),
('user2', '123'),
('admin', '123');

GO

-- Insert data into Customer
INSERT INTO customer (account_id, email, fullname, phone, photo, walletkey) VALUES
(1, 'quangvutran08032004@gmail.com', 'Tran Vu', '0347302926', 'photo1.jpg', 'ErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDSErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDS'),
(2, 'user2@example.com', 'User Two', '0987654321', 'photo2.jpg', 'walletkey2'),
(3, 'user3@example.com', 'User Three', '1122334455', 'photo3.jpg', 'walletkey3');

GO
-- Insert data into Roles
INSERT INTO Roles (Rolename) VALUES
('USER'),
('ADMIN');
GO
use NFTMark;
-- Insert data into Authorities
INSERT INTO authorities (account_id, role_id) VALUES
(1, 1),
(2, 2);
select * from authorities

GO

-- Insert data into ImageNFT
INSERT INTO ImageNFT (Name, Price, Image, Username, Quantity,key_wallet) VALUES
('NFT Art 1', 10.00, 'nft1.jpg', 'user1', 10,'ErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDSErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDS'),
('NFT Art 2', 12.00, 'nft2.jpg', 'user2', 10,'ErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDSErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDS'),
('NFT Art 3', 13.00, 'nft3.jpg', 'admin', 10,'ErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDSErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDS'),
('NFT Art 4', 14.00, 'nft4.jpg', 'user1', 10,'ErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDSErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDS'),
('NFT Art 5', 15.00, 'nft4.jpg', 'user2', 10,'ErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDSErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDS'),
('NFT Art 6', 16.00, 'nft3.jpg', 'admin', 10,'ErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDSErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDS'),
('NFT Art 7', 17.00, 'nft2.jpg', 'user2', 10,'ErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDSErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDS'),
('NFT Art 8', 18.00, 'nft1.jpg', 'user1', 10,'ErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDSErmnLp4YsZPk1hHW6RaPeDbaRKVT5WCHsTDEHvgBZkDS');
GO



INSERT INTO comment (createdate, description, username, nft_id) VALUES
('2024-07-01', 'Great NFT!', 'user1', 1),
('2024-07-02', 'Nice artwork!', 'user2', 2);



GO

-- Insert data into Transactions
INSERT INTO transactions (transactions_date, transactionsdetails, username) VALUES
('2024-07-01', 'Detail 1', 'user1'),
('2024-07-02', 'Detail 2', 'user2');




GO

-- Insert data into TransactionsDetail
INSERT INTO transactions_detail (image, price, transactionsdetails, transactions_id) VALUES
('image1.jpg', 100, 'Detail 1', 1),
('image2.jpg', 200, 'Detail 2', 2);



GO
