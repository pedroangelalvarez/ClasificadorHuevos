//++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//                                                      //
// Class:  MF_xfl_trapezoid                      //
//                                                      //
// Author: Automatically generated by Xfuzzy            //
//                                                      //
//++++++++++++++++++++++++++++++++++++++++++++++++++++++//

package clasificadordehuevos;

public class MF_xfl_trapezoid extends InputMembershipFunction {
 double a;
 double b;
 double c;
 double d;

 public MF_xfl_trapezoid(double min, double max, double step, double single[], double list[]) {
  super.min = min;
  super.max = max;
  super.step = step;
  this.a = single[0];
  this.b = single[1];
  this.c = single[2];
  this.d = single[3];
 }
 public double param(int _i) {
  switch(_i) {
   case 0: return a;
   case 1: return b;
   case 2: return c;
   case 3: return d;
   default: return 0;
  }
 }

 public double isEqual(double x) {
    return (x<a || x>d? 0: (x<b? (x-a)/(b-a) : (x<c?1 : (d-x)/(d-c)))); 
 }
 public double isGreaterOrEqual(double x) {
    return (x<a? 0 : (x>b? 1 : (x-a)/(b-a) )); 
 }
 public double isSmallerOrEqual(double x) {
    return (x<c? 1 : (x>d? 0 : (d-x)/(d-c) )); 
 }
 public double center() {
    return (b+c)/2; 
 }
 public double basis() {
    return (d-a); 
 }
}

