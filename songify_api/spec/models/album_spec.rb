require 'rails_helper'

RSpec.describe Album, type: :model do
  # pending "add some examples to (or delete) #{__FILE__}"
  it { should belong_to(:artist) }
  it { should validate_presence_of(:title) }
end
