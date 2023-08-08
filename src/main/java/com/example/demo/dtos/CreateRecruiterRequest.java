package com.example.demo.dtos;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class CreateRecruiterRequest extends CreateUserDto{

   @NonNull
   private CompanyDto company;
}
