## block hash
import hashlib, struct

ver = 1
prev_block = "000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f"
mrkl_root = "0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098"
time = 1231469665
bits = 486604799
nonce = 2573394689

hex_str = struct.pack("<L", ver) + prev_block.decode('hex')[::-1] +\
  mrkl_root.decode('hex')[::-1] + struct.pack("<LLL", time, bits, nonce)


hash_str = hashlib.sha256(hashlib.sha256(hex_str).digest()).digest()


block_hash = hash_str[::-1].encode('hex_codec')

print(block_hash)