create database DoAn_QLCafe
go
use DoAn_QLCafe

create table NhanVien(
MaNV char(20) primary key,
TenNV nvarchar(50) not null,
GioiTinh nvarchar(50) not null,
DiaChi nvarchar(50) not null,
SDT int,
TaiKhoan nvarchar(30),
MatKhau nvarchar(30),
ChucVu nvarchar(30),
);
go
/*
create table CaLamViec(
MaCaLamViec char(20) primary key ,
TenCaLamViec nvarchar(50) not null,
SoTien money not null,
);

go

create table CT_LuongNhanVien(
MaNV char(20) foreign key references NhanVien(MaNV),
MaCaLamViec char(20) foreign key references CaLamViec(MaCaLamViec),
ThanhTien money not null,
primary key (MaNV,MaCaLamViec),
);
go
*/
create table LoaiSanPham(
MaLH char(20) primary key,
TenLH nvarchar(50),
MoTa nvarchar(50),
)
go


create table SanPham(
MaSP char(20) primary key,
MaLH char(20),
TenSP nvarchar(50),
GiaSP money,
foreign key (MaLH) references LoaiSanPham(MaLH),
);
go

create table KhachHang(
MaKH char(20) primary key,
TenKH nvarchar(50) not null,
DiaChi nvarchar(50) not null,
SDT int not null,
);
go

create table Ban(
MaBan int primary key,
TenBan nvarchar(20),
TrangThai nvarchar(20),
);
insert into Ban values (1, N'Bàn 1', N'Tr?ng');
insert into Ban values (2, N'Bàn 2', N'Tr?ng');
insert into Ban values (3, N'Bàn 3', N'Tr?ng');
go

create table HoaDon(
MaHD char(20) primary key ,
MaNV char(20) foreign key references NhanVien(MaNV),
MaBan int foreign key references Ban(MaBan),
TongMon int,
TongTien money not null,
NgayXuatHD datetime default getdate(),
);
go


create table CT_HoaDon(
MaHD char(20) foreign key references HoaDon(MaHD),
MaSP char(20) foreign key references SanPham(MaSP),
Soluong int not null,
ThanhTien money not null,
NgayXuat datetime default getdate(),
primary key(MaHD,MaSP),
);
go

select * from NhanVien
select * from SanPham join LoaiSanPham on SanPham.MaLH = LoaiSanPham.MaLH
select * from LoaiSanPham
select * from Ban
select * from HoaDon
select * from CT_HoaDon
