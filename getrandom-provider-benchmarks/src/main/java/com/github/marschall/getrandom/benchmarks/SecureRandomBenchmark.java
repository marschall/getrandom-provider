package com.github.marschall.getrandom.benchmarks;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import com.github.marschall.getrandom.GetrandomProvider;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class SecureRandomBenchmark {

  @Param({GetrandomProvider.GETURANDOM, "NativePRNGNonBlocking"})
  public String algorithm;

  private SecureRandom secureRandom;

  private byte[] bytes16;
  private byte[] bytes32;
  private byte[] bytes64;
  private byte[] bytes128;
  private byte[] bytes256;

  @Setup
  public void setup() throws NoSuchAlgorithmException {
    Security.addProvider(new GetrandomProvider());
    this.secureRandom = SecureRandom.getInstance(this.algorithm);
    this.secureRandom.nextBoolean(); // seed
    this.bytes16 = new byte[16];
    this.bytes32 = new byte[32];
    this.bytes64 = new byte[64];
    this.bytes128 = new byte[128];
    this.bytes256 = new byte[256];
  }

  @Benchmark
  public boolean nextBoolean() {
    return this.secureRandom.nextBoolean();
  }

  @Benchmark
  public int nextInt() {
    return this.secureRandom.nextInt();
  }

  @Benchmark
  public long nextLong() {
    return this.secureRandom.nextLong();
  }

  @Benchmark
  public byte[] nextBytes16() {
    this.secureRandom.nextBytes(this.bytes16);
    return this.bytes16;
  }

  @Benchmark
  public byte[] nextBytes32() {
    this.secureRandom.nextBytes(this.bytes32);
    return this.bytes32;
  }

  @Benchmark
  public byte[] nextBytes64() {
    this.secureRandom.nextBytes(this.bytes64);
    return this.bytes64;
  }

  @Benchmark
  public byte[] nextBytes128() {
    this.secureRandom.nextBytes(this.bytes128);
    return this.bytes128;
  }

  @Benchmark
  public byte[] nextBytes256() {
    this.secureRandom.nextBytes(this.bytes256);
    return this.bytes256;
  }

}
