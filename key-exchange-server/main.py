import socket
import random
from Crypto.Util import number

# DH parametreleri
BITS = 2048
g = 2
p = int("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E08A67CC74020BBEA63B139B22514A08798E3404DDE"
        "F9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A63A36210000000000090563E"
        "143F0000B3C556677AFA9F8102A94E60572098D01192C94D01217E680B6A7A14B3D73FA808F4AEE7CDBE0D6EADF222B5"
        "ED02461A764AAB41D3C5E72B98B71C73E2DDBF8DEFC140E1EF7A47CF4608B4D8DCED0CD08E7DB3B5EFA05C4CF03DE17B"
        "2B7E151628AED2A6ABCABD86B0CBA12229C7E8C6311720D59B6A46D8B94A52B", 16)

# Sunucu anahtarları
server_private_key = random.randint(1, p - 1)
server_public_key = pow(g, server_private_key, p)


def diffie_hellman_key_exchange(client_public_key):
    shared_secret = pow(client_public_key, server_private_key, p)
    return shared_secret


def start_server():
    host = '0.0.0.0'  # Tüm IP adreslerinden gelen bağlantıları dinlemek için
    port = 65432

    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((host, port))
        s.listen()
        print(f"Server listening on {host}:{port}")

        while True:
            conn, addr = s.accept()
            with conn:
                print(f"Connected by {addr}")

                # Sunucu public key'i gönderir
                server_public_key_str = str(server_public_key) + '\n'
                conn.sendall(server_public_key_str.encode('utf-8'))
                print(f"Sent server public key: {server_public_key}")

                # İstemci public key'ini alır
                client_public_key = int(conn.recv(1024).decode('utf-8').strip())
                print(f"Received client public key: {client_public_key}")

                # Ortak sır hesaplanır
                shared_secret = diffie_hellman_key_exchange(client_public_key)
                print(f"Shared secret: {shared_secret}")


if __name__ == "__main__":
    start_server()
