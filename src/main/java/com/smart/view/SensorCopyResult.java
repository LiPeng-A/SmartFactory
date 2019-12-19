package com.smart.view;

import lombok.Data;

import java.util.List;

@Data
public class SensorCopyResult {
  private List<Integer> tempList;
  private List<Integer> humList;
  private List<Integer> lightList;
  private List<Integer> pressList;
  private List<Integer> humanList;
}
