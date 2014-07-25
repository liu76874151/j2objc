/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.devtools.j2objc.ast;

import com.google.devtools.j2objc.types.Types;

import org.eclipse.jdt.core.dom.IVariableBinding;

/**
 * Node type for a field access.
 */
public class FieldAccess extends Expression {

  private IVariableBinding variableBinding = null;
  private ChildLink<Expression> expression = ChildLink.create(this);
  private ChildLink<SimpleName> name = ChildLink.create(this);

  public FieldAccess(org.eclipse.jdt.core.dom.FieldAccess jdtNode) {
    super(jdtNode);
    variableBinding = Types.getVariableBinding(jdtNode);
    expression.set((Expression) TreeConverter.convert(jdtNode.getExpression()));
    name.set((SimpleName) TreeConverter.convert(jdtNode.getName()));
  }

  public FieldAccess(FieldAccess other) {
    super(other);
    variableBinding = other.getVariableBinding();
    expression.copyFrom(other.getExpression());
    name.copyFrom(other.getName());
  }

  public IVariableBinding getVariableBinding() {
    return variableBinding;
  }

  public Expression getExpression() {
    return expression.get();
  }

  public SimpleName getName() {
    return name.get();
  }

  @Override
  protected void acceptInner(TreeVisitor visitor) {
    if (visitor.visit(this)) {
      expression.accept(visitor);
      name.accept(visitor);
    }
    visitor.endVisit(this);
  }

  @Override
  public FieldAccess copy() {
    return new FieldAccess(this);
  }
}