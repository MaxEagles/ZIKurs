package com.company;

import java.math.BigInteger;

public class Logic {

    public static long Ch(long x, long y, long z) {
        return (x & y) ^ (~x & z);
    }

    public static long Maj(long x, long y, long z) {
        return (x & y) ^ (x & z) ^ (y & z);
    }

    public static long rotate(long x, int l) {
        return (x >>> l) | (x << (Long.SIZE - l));
    }

    public static long Sigma0(long x) {
        return rotate(x, 28) ^ rotate(x, 34) ^ rotate(x, 39);
    }

    public static long Sigma1(long x) {
        return rotate(x, 14) ^ rotate(x, 18) ^ rotate(x, 41);
    }

    public static long _Sigma0(long x) {
        return rotate(x, 1) ^ rotate(x, 8) ^ (x >>> 7);
    }

    public static long _Sigma1(long x) {
        return rotate(x, 19) ^ rotate(x, 61) ^ (x >>> 6);
    }

    public static byte[] pad(byte[] input) {

        int size = input.length + 17;
        while (size % 128 != 0) {
            size += 1;
        }

        byte[] out = new byte[size];

        for (int i = 0; i < input.length; i++) {
            out[i] = input[i];
        }

        out[input.length] = (byte) 0x80;

        byte[] lenInBytes = BigInteger.valueOf(input.length * 8).toByteArray();

        for (int i = lenInBytes.length; i > 0; i--) {
            out[size - i] = lenInBytes[lenInBytes.length - i];
        }

        return out;
    }

    public static long arrToLong(byte[] input, int j) {
        long v = 0;
        for (int i = 0; i < 8; i++) {
            v = (v << 8) + (input[i + j] & 0xff);
        }
        return v;
    }

    public static long[][] toBlocks(byte[] input) {

        long[][] blocks = new long[input.length / 128][16];

        for (int i = 0; i < input.length / 128; i++) {
            for (int j = 0; j < 16; j++) {
                blocks[i][j] = arrToLong(input, i * 128 + j * 8);
            }
        }
        return blocks;
    }


    public static long[][] Message(long[][] M) {
        long[][] W = new long[M.length][80];

        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < 16; j++) {
                W[i][j] = M[i][j];
            }

            for (int j = 16; j < 80; j++) {
                W[i][j] = _Sigma1(W[i][j-2]) + W[i][j-7] + _Sigma0(W[i][j-15]) + W[i][j-16];
            }
        }

        return W;
    }
}
